package netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接受客户端发送来的数据
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + "：服务器读到的数据->" + byteBuf.toString(Charset.forName("utf-8")));

        //回复客户端
        System.out.println("服务端写出数据：");
        ByteBuf buffer = getByteBuf(ctx);
        ctx.channel().writeAndFlush(buffer);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {

        byte[] bytes = "您好，您已经成功连接到服务器".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);

        return buffer;
    }


}
