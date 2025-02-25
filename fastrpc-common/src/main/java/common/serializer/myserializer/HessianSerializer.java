//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.serializer.myserializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import common.exception.SerializeException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements Serializer {
    public HessianSerializer() {
    }

    public byte[] serialize(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] var4;
            try {
                HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
                hessianOutput.writeObject(obj);
                var4 = byteArrayOutputStream.toByteArray();
            } catch (Throwable var6) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            byteArrayOutputStream.close();
            return var4;
        } catch (IOException var7) {
            throw new SerializeException("Serialization failed");
        }
    }

    public Object deserialize(byte[] bytes, int messageType) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            Object var5;
            try {
                HessianInput hessianInput = new HessianInput(byteArrayInputStream);
                var5 = hessianInput.readObject();
            } catch (Throwable var7) {
                try {
                    byteArrayInputStream.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            byteArrayInputStream.close();
            return var5;
        } catch (IOException var8) {
            throw new SerializeException("Deserialization failed");
        }
    }

    public int getType() {
        return 3;
    }

    public String toString() {
        return "Hessian";
    }
}
