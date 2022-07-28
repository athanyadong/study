package com.athan.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * @aurhor yyh
 * @description   redis 序列化的类
 * @date 2022/7/27 14:02
 */
public class JsonRedisSerializer<T>  implements RedisSerializer<T> {
    private static ParserConfig defaultRedisConfig = new ParserConfig(); //解析器配置
    static {
        defaultRedisConfig.setAutoTypeSupport(true);//设置是否可以设置自动类型
    }

    private Class<T> type; //创建class对象
   public JsonRedisSerializer (Class<T> type){//构造器
    this.type=type;
    }

    /**
     * 将给定对象序列化为二进制数据。
     * 参数：
     * t - 要序列化的对象。可以为空
     * @param t
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {
       if (t==null){
           return new byte[0];
       }
    return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 从给定的二进制数据反序列化对象。
     * 参数：
     * 字节——对象二进制表示。可以为空。
     * @param bytes
     * @return
     * @throws SerializationException
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes==null|| bytes.length <= 0){
            return null;
        }
        String s = new String(bytes, IOUtils.UTF8);
        return JSON.parseObject(s,type, defaultRedisConfig);

    }
}
