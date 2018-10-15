package netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.response.LoginResponsePacket;
import netty.session.Session;
import netty.util.LoginUtil;
import netty.util.SessionUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    // @Override
    // public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //     //创建登陆对象
    //     LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
    //     loginRequestPacket.setUserId(UUID.randomUUID().toString());
    //     loginRequestPacket.setUsername("ji_haha");
    //     loginRequestPacket.setPassword("123456");
    //
    //     //写数据
    //     ctx.channel().writeAndFlush(loginRequestPacket);
    // }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {

        String userId = loginResponsePacket.getUserId();
        String userName = loginResponsePacket.getUserName();

        if (loginResponsePacket.isSuccess()) {
            System.out.println("[" + userName + "] 登陆成功, userId为：" + userId);
            // LoginUtil.markAsLogin(ctx.channel());
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
        } else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭");
    }
}
