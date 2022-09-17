## Flink-Connectors
> Demo 展示 与 配置文件封装

读取配置文件完成 flink -> source 的自动构建  
在启动参数中添加 <mark>-config [config file path]</mark>  
以指定需要读取的配置文件路径
- flink - kafka config
```json
{
  "source": {
    "name": "Flink-Source-Kafka",
    "type": "java.lang.String",
    "props": {
      "bootstrap.servers": "",
      "group.id": "",
      "topics": "",
      "start.offset": "接受以下三种参数 [latest,earliest,committed]",
      "value.deserializer": "org.apache.flink.api.common.serialization.SimpleStringSchema",
      "additional": "采用 key = value; (分号为分隔符)"
    }
  },
  "sinks": [{
    "name": "Flink-Sink-STDOUT",
    "type": "",
    "props": {

    }
  }],
  "jobName": "flink-kafka_test"
}
```
- Flink SinkTo Redis Config
```json
{
  "source": {
    "name": "Flink-Source-UUID",
    "dataType": "java.lang.String",
    "sourceFunc": "cn.lqs.flink.connectors.common.util.UuidSource",
    "props": {
      "interval": "2000",
      "max.size": "300"
    }
  },
  "sinks": [
    {
      "name": "Flink-SinkTo-STDOUT",
      "type": "STDOUT",
      "props": {}
    },
    {
      "name": "Flink-SinkTo-Redis",
      "type": "redis",
      "sinkFunc": "",
      "props": {
        "hostname": "localhost",
        "port": "6379",
        "password": "",
        "mapper": "cn.lqs.flink.connectors.common.config.mapper.RedisStringMapper"
      }
    }
  ],
  "jobName": "flink-redis_test"
}
```