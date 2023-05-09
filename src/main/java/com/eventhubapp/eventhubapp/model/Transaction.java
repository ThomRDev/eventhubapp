package com.eventhubapp.eventhubapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "transactions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

	@Id
	@JsonProperty
	private String id;

	private String subscriptionId;
	private String frameworkAgreementId;
	private String paymentMethodType;
	private List<Product> products;
	private String traceId;
	private String alias;
	private String emailAddress;
	private String transactionDateTime;
	private String creationDate;
	private String subscriptionStatus;
	private Boolean activation;
	private Boolean isDeleted;
}
