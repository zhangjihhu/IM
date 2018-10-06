package netty.protocol;


import lombok.Data;

import static netty.protocol.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
