package br.edu.ufabc.chokitus.mq.instances.ironmq;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.client.AbstractReceiver;
import io.iron.ironmq.Client;
import io.iron.ironmq.Message;
import io.iron.ironmq.Queue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQReceiver extends AbstractReceiver<Client, IronMQMessage> implements IronMQClient {

	private final Map<String, Queue> queues = new HashMap<>();

	public IronMQReceiver(final Client client, final ConfigurationProperties properties) {
		super(client, properties);
	}

	@Override
	public void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQMessage instantiateEmptyMessage() {
		return null;
	}

	@Override
	protected IronMQMessage consumeImpl(final String property) throws Exception {
		final Message msg = getQueue(property).reserve();
		return new IronMQMessage(msg.getBody().getBytes(), property, null);
	}

}
