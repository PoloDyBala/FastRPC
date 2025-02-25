//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.serializer.myserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import common.message.RpcRequest;
import common.message.RpcResponse;

public class JsonSerializer implements Serializer {
    public JsonSerializer() {
    }

    public byte[] serialize(Object obj) {
        byte[] bytes = JSONObject.toJSONBytes(obj, new SerializerFeature[0]);
        return bytes;
    }

    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        Class dataType;
        switch (messageType) {
            case 0:
                RpcRequest request = (RpcRequest)JSON.parseObject(bytes, RpcRequest.class, new Feature[0]);
                Object[] objects = new Object[request.getParams().length];

                for(int i = 0; i < objects.length; ++i) {
                    dataType = request.getParamsType()[i];
                    if (!dataType.isAssignableFrom(request.getParams()[i].getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject)request.getParams()[i], request.getParamsType()[i]);
                    } else {
                        objects[i] = request.getParams()[i];
                    }
                }

                request.setParams(objects);
                obj = request;
                break;
            case 1:
                RpcResponse response = (RpcResponse)JSON.parseObject(bytes, RpcResponse.class, new Feature[0]);
                if (response.getDataType() == null) {
                    obj = RpcResponse.fail("类型为空");
                } else {
                    dataType = response.getDataType();
                    if (response.getData() != null && !dataType.isAssignableFrom(response.getData().getClass())) {
                        response.setData(JSONObject.toJavaObject((JSONObject)response.getData(), dataType));
                    }

                    obj = response;
                }
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }

        return obj;
    }

    public int getType() {
        return 1;
    }

    public String toString() {
        return "Json";
    }
}
