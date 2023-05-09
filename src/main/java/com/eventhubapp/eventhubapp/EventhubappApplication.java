package com.eventhubapp.eventhubapp;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.CreateBatchOptions;
import com.eventhubapp.eventhubapp.constans.Constanst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventhubappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventhubappApplication.class, args);
	}

	@Value("${event.hubs.connection.string}")
	private String eventHubsConnectionString;

	@Bean
	public EventHubProducerClient eventHubProducerClient() {
		return new EventHubClientBuilder()
				.connectionString(eventHubsConnectionString)
				.buildProducerClient();
	}

	@Value("${event.hubs.consumer.group}")
	private String eventHubsConsumerGroup;

	@Bean
	public EventHubConsumerAsyncClient eventHubConsumerAsyncClient() {
		return new EventHubClientBuilder()
				.connectionString(eventHubsConnectionString)
				.consumerGroup(eventHubsConsumerGroup)
				.buildAsyncConsumerClient();
	}

	@Bean
	public CreateBatchOptions createBatchOptions() {
		return new CreateBatchOptions().setMaximumSizeInBytes(Constanst.MAXIMUM_BATCH_SIZE_IN_BYTES);
	}
}
