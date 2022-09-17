package cn.lqs.flink.connectors.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 2022/9/14 18:01
 * created by @lqs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sink {

    private String name;
    private String type;
    private String sinkFunc;
    private Map<String, String> props;

}
