//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.serializer.myserializer;

import com.yuan.pojo.User;
import common.exception.SerializeException;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtostuffSerializer implements Serializer {
    public ProtostuffSerializer() {
    }

    public byte[] serialize(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot serialize null object");
        } else {
            Schema schema = RuntimeSchema.getSchema(obj.getClass());
            LinkedBuffer buffer = LinkedBuffer.allocate(512);

            byte[] bytes;
            try {
                bytes = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
            } finally {
                buffer.clear();
            }

            return bytes;
        }
    }

    public Object deserialize(byte[] bytes, int messageType) {
        if (bytes != null && bytes.length != 0) {
            Class<?> clazz = this.getClassForMessageType(messageType);
            Schema schema = RuntimeSchema.getSchema(clazz);

            Object obj;
            try {
                obj = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception var7) {
                throw new SerializeException("Deserialization failed due to reflection issues");
            }

            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
            return obj;
        } else {
            throw new IllegalArgumentException("Cannot deserialize null or empty byte array");
        }
    }

    public int getType() {
        return 4;
    }

    private Class<?> getClassForMessageType(int messageType) {
        if (messageType == 1) {
            return User.class;
        } else {
            throw new SerializeException("Unknown message type: " + messageType);
        }
    }

    public String toString() {
        return "Protostuff";
    }
}
