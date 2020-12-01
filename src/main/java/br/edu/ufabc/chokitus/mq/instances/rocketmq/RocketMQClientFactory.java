package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;

public class RocketMQClientFactory extends AbstractClientFactory<RocketMQReceiver, RocketMQProducer> {

	public RocketMQClientFactory(final ConfigurationProperties clientFactoryProperties) {
		super(clientFactoryProperties);
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected RocketMQReceiver createConsumerImpl(final ConfigurationProperties consumerProperties) throws Exception {
		final String groupName = (String) consumerProperties.get(RocketMQProperty.GROUP_NAME.getValue());
		final String nameServrAddress = (String) consumerProperties.get(RocketMQProperty.NAME_SERVER_ADDRESS.getValue());
		final String topicName = (String) consumerProperties.get(RocketMQProperty.TOPIC_NAME.getValue());

		final DefaultLitePullConsumer consumer = new DefaultLitePullConsumer(groupName);
		consumer.setNamesrvAddr(nameServrAddress);
		consumer.setPullBatchSize(1); // For now, hardcoded

		consumer.subscribe(topicName, "*");

		return new RocketMQReceiver(consumer, consumerProperties);
	}

	@Override
	protected RocketMQProducer createProducerImpl(final ConfigurationProperties producerProperties) throws Exception {
		final String groupName = (String) producerProperties.get(RocketMQProperty.GROUP_NAME.getValue());
		final String nameServrAddress = (String) producerProperties.get(RocketMQProperty.NAME_SERVER_ADDRESS.getValue());

		final DefaultMQProducer producer = new DefaultMQProducer(groupName);
		producer.setNamesrvAddr(nameServrAddress);

		return new RocketMQProducer(producer, producerProperties);
	}

}
