package com.eventhubapp.eventhubapp.service;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubConsumerAsyncClient;
import com.eventhubapp.eventhubapp.model.Transaction;
import com.eventhubapp.eventhubapp.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EventHubConsumerService {
	@Autowired
	private EventHubConsumerAsyncClient eventHubConsumerAsyncClient;

	@Autowired
	private TransactionRepository transactionRepository;

	@PostConstruct
	public void consumeEvents() {
		Disposable subscription = eventHubConsumerAsyncClient
				.receive()
				.subscribe(partitionEvent -> {
					EventData eventData = partitionEvent.getData();
					String message = new String(eventData.getBody(), StandardCharsets.UTF_8);
					System.out.println("Received message: " + message);
					ObjectMapper objectMapper = new ObjectMapper();
					try {
						List<Transaction> movementReports = objectMapper.readValue(message, new TypeReference<List<Transaction>>(){});
						transactionRepository.insert(movementReports);
					} catch (IOException e) {
						System.err.println("Error parsing message: " + e.getMessage());
					}
				}, error -> {
					System.err.println("Error receiving events: " + error);
				});

		// subscription.dispose();
	}
}
