package br.edu.ufabc.mq.benchmark.instances.rocketmq;

import java.util.Map;

import org.apache.rocketmq.common.message.Message;

import br.edu.ufabc.mq.message.AbstractMessage;

public class RocketMQMessage extends AbstractMessage<Message> {

	public RocketMQMessage(final Message message, final String destination, final Map<String, Object> properties) {
		super(message, destination, properties);
	}

	@Override
	protected String getBodyImpl() {
		return new String(message.getBody());
	}

}
