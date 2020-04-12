package br.edu.ufabc.mq.benchmark.instances.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaHelloWorld {
	private static final String TOPIC = "topic";

	public static void main(final String[] args) {
		final ExecutorService pool = Executors.newFixedThreadPool(2);

		final List<CompletableFuture<Void>> futures = new ArrayList<>();

		futures.add(CompletableFuture.runAsync(() -> doSend()));
		futures.add(CompletableFuture.runAsync(() -> doReceive()));

		futures.forEach(CompletableFuture::join);

		pool.shutdownNow();

	}

	private static void doSend() {
		final Map<String, Object> configs = new HashMap<>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		final KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
		for (int i = 0; i < 100; i++) {
			final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, "key", "Bom dia!");
			producer.send(record);
		}
		producer.flush();
		producer.close();
	}

	private static void doReceive() {
		final Map<String, Object> configs = new HashMap<>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configs.put(ConsumerConfig.GROUP_ID_CONFIG, "teste");
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configs);
		consumer.subscribe(Collections.singletonList(TOPIC));

		final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
		for (final ConsumerRecord<String, String> record : records) {
			System.out.println(record);
		}

		consumer.close();
	}
}
