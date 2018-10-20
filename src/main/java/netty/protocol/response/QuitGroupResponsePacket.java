package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;

import static netty.protocol.command.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {

    private String groupId;

    private boolean success;
    private String reason;



    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }

}
