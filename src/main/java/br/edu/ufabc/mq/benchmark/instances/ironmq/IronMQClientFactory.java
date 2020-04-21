package br.edu.ufabc.mq.benchmark.instances.ironmq;

import java.util.Map;

import java.net.MalformedURLException;

import br.edu.ufabc.mq.factory.AbstractClientFactory;
import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class IronMQClientFactory extends AbstractClientFactory<IronMQConsumer, IronMQProducer> {

	private final String projectId;
	private final String token;
	private final Cloud cloud;

	public IronMQClientFactory(final Map<String, Object> clientFactoryProperties) throws MalformedURLException {
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
	protected IronMQConsumer createConsumerImpl(final Map<String, Object> consumerProperties) throws Exception {
		return new IronMQConsumer(createClient(consumerProperties), consumerProperties);
	}

	@Override
	protected IronMQProducer createProducerImpl(final Map<String, Object> producerProperties) throws Exception {
		return new IronMQProducer(createClient(producerProperties), producerProperties);
	}

	private Client createClient(final Map<String, Object> properties) throws MalformedURLException {
		return new Client((String) properties.get(IronMQProperty.PROJECT_ID.getValue()),
						  (String) properties.get(IronMQProperty.TOKEN.getValue()),
				                   new Cloud((String) properties.get(IronMQProperty.URL.getValue())));
	}
}
