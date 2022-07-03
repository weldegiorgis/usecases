package com.nathy.app.api.command.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("local")
public class AutoCreateConfig {
	
	@Bean
	public NewTopic bidEvents() {
		return TopicBuilder.name("bid-events").partitions(3).replicas(3).build();
	}

}
