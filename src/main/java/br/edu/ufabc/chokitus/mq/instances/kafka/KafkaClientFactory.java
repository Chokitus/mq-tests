package br.edu.ufabc.chokitus.mq.instances.kafka;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class KafkaClientFactory extends AbstractClientFactory<KafkaConsumer, KafkaProducer> {

	public KafkaClientFactory(final Map<String, Object> clientFactoryProperties) {
		super(clientFactoryProperties);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void closeImpl() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected KafkaConsumer createConsumerImpl(final Map<String, Object> consumerProperties) throws Exception {
//		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, IKafkaConstants.KAFKA_BROKERS);
//		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
//		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
//		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, IKafkaConstants.MAX_POLL_RECORDS);
//		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, IKafkaConstants.OFFSET_RESET_EARLIER);
		final org.apache.kafka.clients.consumer.KafkaConsumer<String, byte[]> consumer =
		  new org.apache.kafka.clients.consumer.KafkaConsumer<>(consumerProperties);

		return new KafkaConsumer(consumer, consumerProperties);
	}

	@Override
	protected KafkaProducer createProducerImpl(final Map<String, Object> producerProperties) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
