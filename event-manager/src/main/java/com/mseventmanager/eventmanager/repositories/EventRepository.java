package com.mseventmanager.eventmanager.repositories;

import com.mseventmanager.eventmanager.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

}
