package cn.lqs.flink.connectors.common;


import cn.lqs.flink.connectors.common.config.RedisConfig;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 2022/9/17 10:41
 * created by @lqs
 */
public class SinksBuilder {

    public static <T> void build(List<Sink> sinks, SingleOutputStreamOperator<T> ds) {
        sinks.forEach(sink -> {
            SinkFunction<T> func = null;
            if (sink.getSinkFunc() != null && !"".equals(sink.getSinkFunc())) {
                try {
                    func = (SinkFunction<T>) Class.forName(sink.getSinkFunc()).getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            switch (sink.getType().toLowerCase()){
                case "stdout":
                    buildStdoutSink(ds);
                    break;
                case "redis":
                    RedisConfig.host = sink.getProps().get("hostname");
                    RedisConfig.password = sink.getProps().get("password");
                    RedisConfig.port = Integer.parseInt(sink.getProps().get("port"));
                    try{
                        buildRedisSink(ds, new RedisSink<>(RedisConfig.redisCfg,
                                (RedisMapper<T>) Class.forName(sink.getProps().get("mapper"))
                                        .getDeclaredConstructor().newInstance()));
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("不支持的 sink 类型 :: " + sink.getType());
            }

        });
    }

    private static <T> void buildStdoutSink(SingleOutputStreamOperator<T> ds) {
        try {
            ds.print();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void  buildRedisSink(SingleOutputStreamOperator<T> dataSource,
                                            SinkFunction<T> func){

        dataSource.addSink(func);
    }

}
