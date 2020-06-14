package br.edu.ufabc.chokitus.mq.instances.kafka;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class KafkaClientFactory extends AbstractClientFactory<KafkaConsumer, KafkaProducer> {

	public KafkaClientFactory(final Map<String, Object> clientFactoryProperties) {
		super(clientFactoryProperties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected KafkaConsumer createConsumerImpl(final Map<String, Object> consumerProperties) throws Exception {
		final Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, clientFactoryProperties.get(KafkaProperty.SERVER_ADDRESS.getValue()));
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.get(KafkaProperty.CONSUMER_GROUP_ID.getValue()));
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProperties.get(KafkaProperty.KEY_DESERIALIZER.getValue()));
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProperties.get(KafkaProperty.VALUE_DESERIALIZER.getValue()));
		properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerProperties.get(KafkaProperty.MAX_POLL_RECORDS.getValue()));
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerProperties.get(KafkaProperty.AUTO_COMMIT.getValue()));
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProperties.get(KafkaProperty.OFFSET_RESET.getValue()));
		final org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]> consumer =
		  new org.apache.kafka.clients.consumer.KafkaConsumer<>(consumerProperties);

		return new KafkaConsumer(consumer, consumerProperties);
	}

	@Override
	protected KafkaProducer createProducerImpl(final Map<String, Object> producerProperties) throws Exception {
		final Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, clientFactoryProperties.get(KafkaProperty.SERVER_ADDRESS.getValue()));
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, producerProperties.get(KafkaProperty.CLIENT_ID.getValue()));
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.get(KafkaProperty.KEY_SERIALIZER.getValue()));
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.get(KafkaProperty.VALUE_SERIALIZER.getValue()));

		final org.apache.kafka.clients.producer.KafkaProducer<String, byte[]> producer =
		  new org.apache.kafka.clients.producer.KafkaProducer<>(properties);

		return new KafkaProducer(producer, producerProperties);
	}

}
