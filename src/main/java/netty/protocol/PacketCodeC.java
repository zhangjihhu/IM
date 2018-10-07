package netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import netty.protocol.request.LoginRequestPacket;
import netty.protocol.response.LoginResponsePacket;
import netty.serialize.Serializer;
import netty.serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static netty.protocol.command.Command.LOGIN_REQUEST;
import static netty.protocol.command.Command.LOGIN_RESPONSE;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        //1.创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.DEFAULT.ioBuffer();
        //2.序列 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //3.实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //1.跳过magic number
        byteBuf.skipBytes(4);
        //2.跳过版本号
        byteBuf.skipBytes(1);
        //3.序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        //4.指令
        byte command = byteBuf.readByte();
        //5.数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);

        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }





}
