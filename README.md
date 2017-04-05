# thrift-stack
thrift encode/decode , RPC
##### Some samples for thrift encode and decode data , powered by apache thrift 0.9.2 and apache kafka_2_10_0.9.0.0

##### IDL: see {projectroot}/src/thrift/data.thrift

##### For testing

    Kafka kafka_2.10-0.9.0.0  on port 9092
    Zookeeper zookeeper-3.4.6 on port 2181

    /data/apps/opt/kafka/bin/kafka-topics.sh --create --topic test_thrift_encode --zookeeper localhost:2181 --partitions 3 --replication-factor 1
    /data/apps/opt/kafka/bin/kafka-console-consumer.sh -zookeeper localhost:2181 --from-beginning --topic test_thrift_encode
    /data/apps/opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test_thrift_encode
