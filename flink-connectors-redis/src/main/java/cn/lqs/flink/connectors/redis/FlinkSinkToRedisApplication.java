package cn.lqs.flink.connectors.redis;

import cn.lqs.flink.connectors.common.SourceBuilder;
import cn.lqs.flink.connectors.common.util.LqsLocalFlinkApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 2022/9/17 12:30
 * created by @lqs
 */
@Slf4j
public class FlinkSinkToRedisApplication {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        // 全自动构建 source
        DataStreamSource<Object> ds = LqsLocalFlinkApplication
                .runLocal(8081, 2)
                .addSource(SourceBuilder.buildSourceFunc(args));
        // 自定义处理逻辑
        SingleOutputStreamOperator<Tuple2<String, String>> ds_ = ds.map(uuid-> {
            String $uuid = (String) uuid;
            return Tuple2.of(DTF.format(LocalDateTime.now()),
                    $uuid.replaceAll("-", "").toLowerCase());
        }).returns(Types.TUPLE(Types.STRING, Types.STRING));
        // 提交执行
        LqsLocalFlinkApplication.<Tuple2<String, String>>doExecute(ds_);
    }

}
