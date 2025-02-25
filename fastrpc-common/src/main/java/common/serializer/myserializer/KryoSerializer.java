//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.serializer.myserializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.yuan.pojo.User;
import common.exception.SerializeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer {
    private Kryo kryo = new Kryo();

    public KryoSerializer() {
    }

    public byte[] serialize(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot serialize null object");
        } else {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] var4;
                try {
                    Output output = new Output(byteArrayOutputStream);

                    try {
                        this.kryo.writeObject(output, obj);
                        var4 = output.toBytes();
                    } catch (Throwable var8) {
                        try {
                            output.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }

                        throw var8;
                    }

                    output.close();
                } catch (Throwable var9) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable var6) {
                        var9.addSuppressed(var6);
                    }

                    throw var9;
                }

                byteArrayOutputStream.close();
                return var4;
            } catch (Exception var10) {
                throw new SerializeException("Serialization failed");
            }
        }
    }

    public Object deserialize(byte[] bytes, int messageType) {
        if (bytes != null && bytes.length != 0) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                Object var6;
                try {
                    Input input = new Input(byteArrayInputStream);

                    try {
                        Class<?> clazz = this.getClassForMessageType(messageType);
                        var6 = this.kryo.readObject(input, clazz);
                    } catch (Throwable var9) {
                        try {
                            input.close();
                        } catch (Throwable var8) {
                            var9.addSuppressed(var8);
                        }

                        throw var9;
                    }

                    input.close();
                } catch (Throwable var10) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable var7) {
                        var10.addSuppressed(var7);
                    }

                    throw var10;
                }

                byteArrayInputStream.close();
                return var6;
            } catch (Exception var11) {
                throw new SerializeException("Deserialization failed");
            }
        } else {
            throw new IllegalArgumentException("Cannot deserialize null or empty byte array");
        }
    }

    public int getType() {
        return 2;
    }

    private Class<?> getClassForMessageType(int messageType) {
        if (messageType == 1) {
            return User.class;
        } else {
            throw new SerializeException("Unknown message type: " + messageType);
        }
    }

    public String toString() {
        return "Kryo";
    }
}
