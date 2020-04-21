package br.edu.ufabc.mq.benchmark.instances.ironmq;

import java.util.HashMap;
import java.util.Map;

import br.edu.ufabc.mq.client.AbstractProducer;
import io.iron.ironmq.Client;
import io.iron.ironmq.Queue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQProducer extends AbstractProducer<Client, IronMQMessage> implements IronMQClient {

	private final Map<String, Queue> queues = new HashMap<>();

	public IronMQProducer(final Client client, final Map<String, Object> properties) {
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
	protected IronMQMessage sendImpl(final IronMQMessage message) throws Exception {
		final String destination = message.getDestination();
		final Queue queue = getQueue(destination);

		final String messageId = queue.push(message.getBody());

		return new IronMQMessage(messageId, destination, message.getProperties());
	}

}
