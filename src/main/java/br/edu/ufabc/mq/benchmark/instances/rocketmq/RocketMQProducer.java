package br.edu.ufabc.mq.benchmark.instances.rocketmq;

import java.util.Map;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import br.edu.ufabc.mq.client.AbstractProducer;

public class RocketMQProducer extends AbstractProducer<DefaultMQProducer, RocketMQMessage> {

	public RocketMQProducer(final DefaultMQProducer client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.shutdown();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		client.start();
	}

	@Override
	protected RocketMQMessage sendImpl(final RocketMQMessage message) throws Exception {
		client.send(message.getMessage());
		return null;
	}

}
