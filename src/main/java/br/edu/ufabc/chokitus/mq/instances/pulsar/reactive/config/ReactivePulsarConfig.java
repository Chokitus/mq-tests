package br.edu.ufabc.chokitus.mq.instances.pulsar.reactive.config;

import br.edu.ufabc.chokitus.reactive.mq.config.ReactiveConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ReactivePulsarConfig extends ReactiveConfig {

	@Getter
	@RequiredArgsConstructor
	public enum Properties implements ReactiveProperty {
		TOPIC_NAME("topic_name"),
		SUBSCRIPTION_NAME("subscription_name");

		private final String v;

		@Override
		public String v() {
			return v;
		}
	}

	private String serviceUrl;

}
