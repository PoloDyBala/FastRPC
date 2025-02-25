//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.serializer.mycoder;

import common.message.MessageType;
import common.message.RpcRequest;
import common.message.RpcResponse;
import common.serializer.myserializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyEncoder extends MessageToByteEncoder {
    private static final Logger log = LoggerFactory.getLogger(MyEncoder.class);
    private Serializer serializer;

    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        log.debug("Encoding message of type: {}", msg.getClass());
        if (msg instanceof RpcRequest) {
            out.writeShort(MessageType.REQUEST.getCode());
        } else {
            if (!(msg instanceof RpcResponse)) {
                log.error("Unknown message type: {}", msg.getClass());
                throw new IllegalArgumentException("Unknown message type: " + msg.getClass());
            }

            out.writeShort(MessageType.RESPONSE.getCode());
        }

        out.writeShort(this.serializer.getType());
        byte[] serializeBytes = this.serializer.serialize(msg);
        if (serializeBytes != null && serializeBytes.length != 0) {
            out.writeInt(serializeBytes.length);
            out.writeBytes(serializeBytes);
        } else {
            throw new IllegalArgumentException("Serialized message is empty");
        }
    }

    public MyEncoder(Serializer serializer) {
        this.serializer = serializer;
    }
}
