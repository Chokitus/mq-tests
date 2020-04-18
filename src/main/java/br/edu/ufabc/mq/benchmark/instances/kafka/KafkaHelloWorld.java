package br.edu.ufabc.mq.benchmark.instances.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaHelloWorld {
	private static final String LOCALHOST_9092 = "localhost:9092";

	private static final String GROUP = "group";

	private static final String TOPIC_NAME = "topic";

	public static Logger LOGGER = LoggerFactory.getLogger(KafkaHelloWorld.class);

	public static void main(final String[] args) {
		final ExecutorService pool = Executors.newFixedThreadPool(2);

		final List<CompletableFuture<Void>> futures = new ArrayList<>();

		final Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		final AdminClient client = AdminClient.create(kafkaProps);
		client.createTopics(Collections.singletonList(new NewTopic(TOPIC_NAME, 1, (short) 1)));
		client.close();

		doSend();
		futures.add(CompletableFuture.runAsync(() -> doReceive()));

		futures.forEach(CompletableFuture::join);

		pool.shutdownNow();
	}

	private static void doSend() {

		final Properties kafkaProps = new Properties();
		kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		try (final Producer<String, String> producer = new KafkaProducer<>(kafkaProps)) {
			final String inputText = null;
			for (int i = 0; i < 100; i++) {
				// key is hardcoded to 'key1', which forces all messages to go to a single
				// partition as per kafka behavior
				final ProducerRecord<String, String> recordToSend = new ProducerRecord<>(TOPIC_NAME, "key1", inputText);

				// asynchronous send
				producer.send(recordToSend, (recordMetadata, e) -> {
					if (e == null) {
						LOGGER.info("Message Sent. topic={}, partition={}, offset={}", recordMetadata.topic(),
								recordMetadata.partition(), recordMetadata.offset());
					} else {
						LOGGER.error("Error while sending message. ", e);
					}
				});
			}
			producer.flush();
		}
	}

	private static void doReceive() {
		// consume messages
		final Properties kafkaProps = new Properties();
		kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCALHOST_9092);
		kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
		kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProps);

		// subscribe to the test topic
		consumer.subscribe(Collections.singletonList(TOPIC_NAME));
		try {
			String receivedText = null;
			while (!"exit".equalsIgnoreCase(receivedText)) {
				final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (final ConsumerRecord<String, String> record : records) {
					receivedText = record.value();
					if (receivedText != null) {
						LOGGER.info(
								"Message received ==> topic = {}, partition = {}, offset = {}, key = {}, value = {}",
								record.topic(), record.partition(), record.offset(), record.key(), receivedText);
					}
				}
			}
		} finally {
			consumer.close();
		}
	}
}
