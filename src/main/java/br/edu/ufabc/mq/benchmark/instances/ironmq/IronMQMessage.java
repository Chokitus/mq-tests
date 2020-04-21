package br.edu.ufabc.mq.benchmark.instances.ironmq;

import java.util.Map;

import br.edu.ufabc.mq.message.AbstractMessage;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQMessage extends AbstractMessage<String> {

	public IronMQMessage(final String message, final String destination, final Map<String, Object> properties) {
		super(message, destination, properties);
	}

	@Override
	protected String getBodyImpl() {
		return message;
	}

}
