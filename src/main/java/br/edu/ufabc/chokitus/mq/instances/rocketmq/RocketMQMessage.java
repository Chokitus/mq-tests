package br.edu.ufabc.chokitus.mq.instances.rocketmq;

import java.util.Map;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import org.apache.rocketmq.common.message.Message;

import br.edu.ufabc.chokitus.mq.message.AbstractMessage;

public class RocketMQMessage extends AbstractMessage<Message> {

	public RocketMQMessage(final Message message, final String destination, final ConfigurationProperties properties) {
		super(message, destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message.getBody();
	}

}
