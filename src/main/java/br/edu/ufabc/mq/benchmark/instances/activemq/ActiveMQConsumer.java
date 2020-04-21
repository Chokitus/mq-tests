package br.edu.ufabc.mq.benchmark.instances.activemq;

import java.util.Map;

import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientSession;

import br.edu.ufabc.mq.client.AbstractConsumer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActiveMQConsumer extends AbstractConsumer<ClientConsumer, ActiveMQMessage> implements ActiveMQClient {

	private final ClientSession session;

	public ActiveMQConsumer(final ClientConsumer client, final ClientSession session, final Map<String, Object> properties) {
		super(client, properties);
		this.session = session;
	}

	@Override
	public void closeImpl() throws ActiveMQException {
		client.close();
	}

	@Override
	public void startImpl(final Map<String, Object> properties) {
		throw new UnsupportedOperationException("Um cliente do ActiveMQ deve ser iniciado através de seu objeto Session");
	}

	@Override
	protected ActiveMQMessage consumeImpl(final String property) throws ActiveMQException {
		final ClientMessage message = client.receive();
		return new ActiveMQMessage(message);
	}

}
