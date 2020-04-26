package br.edu.ufabc.mq.benchmark.instances.ironmq;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.mq.client.AbstractConsumer;
import io.iron.ironmq.Client;
import io.iron.ironmq.Message;
import io.iron.ironmq.Queue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQConsumer extends AbstractConsumer<Client, IronMQMessage> implements IronMQClient {

	private final Map<String, Queue> queues = new HashMap<>();

	public IronMQConsumer(final Client client, final Map<String, Object> properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	public void startImpl(final Map<String, Object> properties) throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQMessage consumeImpl(final String property) throws Exception {
		final Message msg = getQueue(property).reserve();
		return new IronMQMessage(msg.getBody().getBytes(), property, null);
	}

}
