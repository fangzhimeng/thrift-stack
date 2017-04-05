package cn.kenshinn.thrift.kafka;

import cn.kenshinn.thrift.thrift.models.DemoModel;
import cn.kenshinn.thrift.thrift.protocol.DemoModelEncoder;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaProducer {

	private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	private Producer<String, DemoModel> producer;

	public KafkaProducer(){
		Properties props = new Properties();
		props.put("metadata.broker.list", "localhost:9092");
		props.put("request.required.acks", "1");
		props.put("message.send.max.retries", "20");
		props.put("key.serializer.class", StringEncoder.class.getName());
		props.put("serializer.class", DemoModelEncoder.class.getName());

		producer = new Producer<>(new ProducerConfig(props));
	}
	
	public void process(String topic, DemoModel msg){

		try {
			producer.send(new KeyedMessage<>(topic, msg));

			LOGGER.info("sent message to topic[{}], msg:[{}]", topic, msg.toString());
		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}

	}
	
	public void process(String topic, String key, DemoModel asData) {
		
		try {
			producer.send(new KeyedMessage<>(topic, key, key, asData));

			LOGGER.info("sent message to topic[{}], key:[{}], msg:[{}]", topic, key, asData.toString());
		} catch (Exception e) {
			LOGGER.error("Exception", e);
		}
		
	}
	
	protected void finalize(){
		if(null != producer)
			producer.close();
	}

}
