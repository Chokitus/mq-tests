package br.edu.ufabc.mq.benchmark.instances.ironmq;

import java.util.Map;

import br.edu.ufabc.mq.message.AbstractMessage;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQMessage extends AbstractMessage<byte[]> {

	public IronMQMessage(final byte[] message, final String destination, final Map<String, Object> properties) {
		super(message, destination, properties);
	}

	@Override
	protected byte[] getBodyImpl() {
		return message;
	}

}
