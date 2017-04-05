package cn.kenshinn.thrift.kafka;

import cn.kenshinn.thrift.thrift.models.DemoModel;
import cn.kenshinn.thrift.thrift.protocol.DemoModelDecoder;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	private ConsumerConfig consumerConfig = null;

	private static int numPartions = 3;

	private final ExecutorService executor = Executors.newFixedThreadPool(numPartions * 10);

	public KafkaConsumer() {
		// config props should be extract out into some config files and should be check here
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "test_consumer");
		props.put("auto.offset.reset", "largest");
		props.put("fetch.message.max.bytes", "204857600");
		props.put("zookeeper.sync.time.ms", "2000");

		props.put("zookeeper.session.timeout.ms", "5000");
		props.put("zookeeper.connection.timeout.ms", "10000");
		props.put("rebalance.backoff.ms", "2000");
		props.put("rebalance.max.retries", "10");

		consumerConfig = new ConsumerConfig(props);
		
		LOGGER.info("success init kafka consumer");
	}


	public void start(String channel){

		List<KafkaStream<String, DemoModel>> streams = getPartitionsStreams(channel, numPartions);

		for (final KafkaStream<String, DemoModel> stream : streams) {
			// Make consuming parallelization for many partitions
			executor.submit(() -> {
				try {
					for (MessageAndMetadata<String, DemoModel> message : stream) {
						DemoModel asData = message.message();
						if (Objects.nonNull(asData)) {
							LOGGER.warn("no data deserialized ..., thread:{}", Thread.currentThread().getName());
						}
					}
				} catch (Exception e1) {
					LOGGER.error("kafka consumer runs into error.", e1);
				}
			});

			LOGGER.info("submit kafka stream client to thread pool");
		}
	}


	private List<KafkaStream<String, DemoModel>> getPartitionsStreams(String channel, int numpartions) {
		LOGGER.info("listen kafka topic : {} with partions : {}", channel, numpartions+"");

		final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(consumerConfig);

		HashMap<String, Integer> channelMap = new HashMap<>();
		channelMap.put(channel, numpartions);

		Map<String, List<KafkaStream<String, DemoModel>>> partitions =
				consumer.createMessageStreams(
						channelMap,
						new StringDecoder(new VerifiableProperties()),
						new DemoModelDecoder(new VerifiableProperties())
				);

		return partitions.get(channel);
	}


}
