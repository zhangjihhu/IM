package netty.client.console;

import io.netty.channel.Channel;
import netty.protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个用户(userID message)：");

        String toUserId = scanner.next();
        String message = scanner.next();

        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }

}
