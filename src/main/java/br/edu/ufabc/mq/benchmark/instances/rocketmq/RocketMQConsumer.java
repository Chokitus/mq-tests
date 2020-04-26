package br.edu.ufabc.mq.benchmark.instances.rocketmq;

import java.util.Map;

import org.apache.rocketmq.client.consumer.LitePullConsumer;

import br.edu.ufabc.mq.client.AbstractConsumer;

public class RocketMQConsumer extends AbstractConsumer<LitePullConsumer, RocketMQMessage> {

	public RocketMQConsumer(final LitePullConsumer client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		client.start();
	}

	@Override
	protected RocketMQMessage consumeImpl(final String property) throws Exception {
		return null;
	}

}
