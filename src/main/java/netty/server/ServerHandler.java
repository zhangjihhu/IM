package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protocol.Packet;
import netty.protocol.PacketCodeC;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.request.MessageRequestPacket;
import netty.protocol.response.LoginResponsePacket;
import netty.protocol.response.MessageResponsePacket;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket) {
            System.out.println(new Date() + "：收到客户端登陆请求......");
            //登陆流程
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess((true));
                System.out.println(new Date() + ":登陆成功");
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + ":登陆失败");
            }
            //登陆响应
            ByteBuf responseBytBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseBytBuf);

        } else if (packet instanceof MessageRequestPacket) {
            //客户端发来消息
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ":收到客户端消息：" + messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("客户端回复【"+ messageRequestPacket.getMessage() +"】");
            ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(byteBuf);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }


}
