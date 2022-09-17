package cn.lqs.flink.connectors.common;


import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 2022/9/17 10:41
 * created by @lqs
 */
public class SinksBuilder {

    public static <T> void build(List<Sink> sinks, DataStreamSource<T> ds) {
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
                    buildRedisSink(ds, func);
                    break;
                default:
                    throw new IllegalArgumentException("不支持的 sink 类型 :: " + sink.getType());
            }

        });
    }

    private static <T> void buildStdoutSink(DataStreamSource<T> ds) {
        try {
            ds.print();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> void  buildRedisSink(DataStreamSource<T> dataSource,
                                            SinkFunction<T> func){
        dataSource.addSink(func);
    }

}
