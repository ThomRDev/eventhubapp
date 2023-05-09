package com.eventhubapp.eventhubapp.repository;

import com.eventhubapp.eventhubapp.model.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
