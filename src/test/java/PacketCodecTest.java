import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.Packet;
import netty.protocol.PacketCodeC;
import netty.serialize.Serializer;
import netty.serialize.impl.JSONSerializer;
import org.junit.Assert;
import org.junit.Test;

public class PacketCodecTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUsername("zhangji");
        loginRequestPacket.setPassword("password");


        // PacketCodeC packetCodeC = new PacketCodeC();
        ByteBufAllocator byteBufAllocator = null;

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(byteBufAllocator, loginRequestPacket);
        System.out.println(byteBuf);

        Packet decodedPacket = PacketCodeC.INSTANCE.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));


    }


}
