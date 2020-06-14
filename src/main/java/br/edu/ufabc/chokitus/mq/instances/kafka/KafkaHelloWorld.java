package br.edu.ufabc.chokitus.mq.instances.kafka;

import java.util.Collections;
import java.util.Properties;

import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaHelloWorld {

	public static void main(final String[] args) {
		runProducer();
		runConsumer();
	}

	static void runConsumer() {
		final KafkaConsumer<Long, String> consumer = createConsumer();

		final ConsumerRecords<Long, String> consumerRecords = consumer.poll(Duration.ofMillis(5000));

		consumerRecords.forEach(record -> System.out.println(record.value()));
		// commits the offset of record to broker.
		consumer.commitAsync();
		consumer.close();
	}

	static void runProducer() {
		try (final KafkaProducer<Long, String> producer = createProducer()) {
			for (int index = 0; index < IKafkaConstants.MESSAGE_COUNT; index++) {
				final ProducerRecord<Long, String> record = new ProducerRecord<>(IKafkaConstants.TOPIC_NAME,
						"This is record " + index);
				try {
					System.out.println("Enviando...");
					producer.send(record);
					System.out.println("Enviado!");
				} catch (final Exception e) {
					System.out.println(e);
				}
			}
			System.out.println("Saindo!");
		}
		System.out.println("Sai!");
	}

	public static KafkaProducer<Long, String> createProducer() {
		final Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "client1");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

	public static KafkaConsumer<Long, String> createConsumer() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);
		final KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(IKafkaConstants.TOPIC_NAME));
		return consumer;
	}

}
