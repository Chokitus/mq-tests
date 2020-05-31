package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import java.util.List;
import java.util.Map;

import org.apache.rocketmq.client.consumer.LitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import br.edu.ufabc.mq.client.AbstractConsumer;

public class RocketMQConsumer extends AbstractConsumer<LitePullConsumer, RocketMQMessage> {

	public RocketMQConsumer(final LitePullConsumer client, final Map<String, Object> properties) {
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
