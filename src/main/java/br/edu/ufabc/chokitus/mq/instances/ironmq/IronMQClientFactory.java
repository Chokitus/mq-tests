package br.edu.ufabc.chokitus.mq.instances.ironmq;

import java.net.MalformedURLException;

import br.edu.ufabc.chokitus.mq.benchmark.ConfigurationProperties;
import br.edu.ufabc.chokitus.mq.factory.AbstractClientFactory;
import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQClientFactory extends AbstractClientFactory<IronMQReceiver, IronMQProducer> {

	private final String projectId;
	private final String token;
	private final Cloud cloud;

	public IronMQClientFactory(final ConfigurationProperties clientFactoryProperties) throws MalformedURLException {
		super(clientFactoryProperties);
		projectId = (String) clientFactoryProperties.get(IronMQProperty.PROJECT_ID.getValue());
		token = (String) clientFactoryProperties.get(IronMQProperty.TOKEN.getValue());
		cloud = new Cloud((String) clientFactoryProperties.get(IronMQProperty.URL.getValue()));
	}

	@Override
	protected void closeImpl() throws Exception {
		// Unnecessary
	}

	@Override
	protected IronMQReceiver createConsumerImpl(final ConfigurationProperties consumerProperties) throws Exception {
		return new IronMQReceiver(createClient(consumerProperties), consumerProperties);
	}

	@Override
	protected IronMQProducer createProducerImpl(final ConfigurationProperties producerProperties) throws Exception {
		return new IronMQProducer(createClient(producerProperties), producerProperties);
	}

	private Client createClient(final ConfigurationProperties properties) throws MalformedURLException {
		return new Client((String) properties.get(IronMQProperty.PROJECT_ID.getValue()),
						  (String) properties.get(IronMQProperty.TOKEN.getValue()),
				                   new Cloud((String) properties.get(IronMQProperty.URL.getValue())));
	}
}
