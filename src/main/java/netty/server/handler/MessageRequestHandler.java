package netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.protocol.request.MessageRequestPacket;
import netty.protocol.response.MessageResponsePacket;
import netty.session.Session;
import netty.util.SessionUtil;


public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        //1.获取消息发送方的会话消息
        Session session = SessionUtil.getSession(ctx.channel());

        //2.通过消息发送方的会话信息构造要发送的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //4.将消息发送给接受方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println("[" + session.getUserId() + "] 不在线，发送失败");
        }
        // MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        // System.out.println(new Date() + ":收到客户端消息：" + messageRequestPacket.getMessage());
        // messageResponsePacket.setMessage("客户端回复【"+ messageRequestPacket.getMessage() +"】");

        // ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
