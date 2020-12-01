package br.edu.ufabc.chokitus.mq.instances.kafka.blocking.factory;

import java.util.Properties;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.config.KafkaProperty;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.client.KafkaProducer;
import br.edu.ufabc.chokitus.mq.instances.kafka.blocking.client.KafkaReceiver;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class KafkaClientFactory extends AbstractClientFactory<KafkaReceiver, KafkaProducer> {

	public KafkaClientFactory(final ConfigurationProperties clientFactoryProperties) {
		super(clientFactoryProperties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected KafkaReceiver createConsumerImpl(final ConfigurationProperties consumerProperties) throws Exception {
		final Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, clientFactoryProperties.getProp(KafkaProperty.SERVER_ADDRESS.getValue()));
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.getProp(KafkaProperty.CONSUMER_GROUP_ID.getValue()));
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProperties.getProp(KafkaProperty.KEY_DESERIALIZER.getValue()));
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProperties.getProp(KafkaProperty.VALUE_DESERIALIZER.getValue()));
		properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerProperties.getProp(KafkaProperty.MAX_POLL_RECORDS.getValue()));
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerProperties.getProp(KafkaProperty.AUTO_COMMIT.getValue()));
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProperties.getProp(KafkaProperty.OFFSET_RESET.getValue()));
		final org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]> consumer =
		  new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);

		return new KafkaReceiver(consumer, consumerProperties);
	}

	@Override
	protected KafkaProducer createProducerImpl(final ConfigurationProperties producerProperties) throws Exception {
		final Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, clientFactoryProperties.getProp(KafkaProperty.SERVER_ADDRESS.getValue()));
		properties.put(ProducerConfig.CLIENT_ID_CONFIG, producerProperties.getProp(KafkaProperty.CLIENT_ID.getValue()));
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getProp(KafkaProperty.KEY_SERIALIZER.getValue()));
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getProp(KafkaProperty.VALUE_SERIALIZER.getValue()));

		final org.apache.kafka.clients.producer.KafkaProducer<String, byte[]> producer =
		  new org.apache.kafka.clients.producer.KafkaProducer<>(properties);

		return new KafkaProducer(producer, producerProperties);
	}

}
