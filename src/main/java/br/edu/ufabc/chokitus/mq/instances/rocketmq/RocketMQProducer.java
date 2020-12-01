package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;

public class RocketMQProducer extends AbstractProducer<DefaultMQProducer, RocketMQMessage> {

	public RocketMQProducer(final DefaultMQProducer client, final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		client.shutdown();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		client.start();
	}

	@Override
	protected RocketMQMessage sendImpl(final RocketMQMessage message) throws Exception {
		client.send(message.getMessage());
		return null;
	}

}
