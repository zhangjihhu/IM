package netty.protocol.response;

import lombok.Data;
import netty.protocol.Packet;
import netty.session.Session;

import java.util.List;

import static netty.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }

}
