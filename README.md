## Flink-Connectors
> Demo 展示 与 配置文件封装

读取配置文件完成 flink -> source 的自动构建
- 配置文件模板
```json
{
  "source": {
    "name": "指定此 source 在 flink server 中的名称",
    "type": "source 类型 ?? "
  },
  "sink": {
    "name": "指定此 sink 在 flink server 中的名称"
  }
}
```