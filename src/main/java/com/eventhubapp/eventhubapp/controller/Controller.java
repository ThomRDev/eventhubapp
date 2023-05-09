package com.eventhubapp.eventhubapp.controller;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.CreateBatchOptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/events")
public class Controller {

	@Autowired
	private EventHubProducerClient eventHubProducerClient;

	@Autowired
	private CreateBatchOptions createBatchOptions;


	@PostMapping
	public ResponseEntity<?> send(@RequestParam("file") MultipartFile file){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// Leemos el contenido del archivo como String
			String jsonContent = new String(file.getBytes());

			EventData eventData = new EventData(jsonContent.getBytes(StandardCharsets.UTF_8));
			List<EventData> eventDataList = Collections.singletonList(eventData);
			eventHubProducerClient.send(eventDataList);

			return ResponseEntity.ok("Archivo procesado correctamente");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el archivo");
		}
	}
}
