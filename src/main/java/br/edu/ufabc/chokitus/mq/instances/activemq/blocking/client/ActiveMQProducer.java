package br.edu.ufabc.chokitus.mq.instances.activemq.blocking.client;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.instances.activemq.blocking.message.ActiveMQMessage;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;

import br.edu.ufabc.chokitus.mq.client.AbstractProducer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActiveMQProducer extends AbstractProducer<ClientProducer, ActiveMQMessage> implements ActiveMQClient {

	private final ClientSession session;

	public ActiveMQProducer(final ClientProducer client, final ClientSession session, final ConfigurationProperties properties) {
		super(client, properties);
		this.session = session;
	}

	@Override
	public void closeImpl() throws ActiveMQException {
		client.close();
	}

	@Override
	public void startImpl(final ConfigurationProperties properties) throws Exception {
		throw new UnsupportedOperationException("Um cliente do ActiveMQ deve ser iniciado através de seu objeto Session");
	}

	@Override
	protected ActiveMQMessage sendImpl(final ActiveMQMessage message) throws ActiveMQException {
		client.send(message.getMessage());
		return null;
	}

}
