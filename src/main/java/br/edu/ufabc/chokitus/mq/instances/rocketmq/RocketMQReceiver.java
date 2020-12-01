package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import java.util.List;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;

public class RocketMQReceiver extends AbstractReceiver<LitePullConsumer, RocketMQMessage> {

	public RocketMQReceiver(final LitePullConsumer client, final ConfigurationProperties properties) {
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
	protected RocketMQMessage instantiateEmptyMessage() {
		return null;
	}

	/**
	 * This depends on the property pullBatchSize on the consumer to be set to one.
	 * Future testing will tell if this method will return a list or a single
	 * message.
	 */
	@Override
	protected RocketMQMessage consumeImpl(final String property) throws Exception {
		final List<MessageExt> poll = client.poll();
		if(!poll.isEmpty()) {
			final MessageExt msg = poll.get(0);
			return new RocketMQMessage(msg, property, null);
		}
		return null;
	}

}
