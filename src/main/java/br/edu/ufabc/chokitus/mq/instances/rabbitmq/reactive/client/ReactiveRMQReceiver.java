package br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.client;

import br.edu.ufabc.chokitus.mq.instances.rabbitmq.reactive.message.ReactiveRMQMessage;
import br.edu.ufabc.chokitus.reactive.mq.client.ReactiveReceiver;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.Receiver;

@RequiredArgsConstructor
public class ReactiveRMQReceiver implements ReactiveReceiver<ReactiveRMQMessage> {

	private final Receiver receiver;

	@Override
	public Flux<ReactiveRMQMessage> consume(final String queue) {
		return receiver.consumeManualAck(queue).map(ReactiveRMQMessage::manualAck);
	}

	@Override
	public Flux<ReactiveRMQMessage> consumeAutoAck(final String queue) {
		return receiver.consumeAutoAck(queue).map(ReactiveRMQMessage::autoAck);
	}

	@Override
	public void close() {
		receiver.close();
	}
}
