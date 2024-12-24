package com.mseventmanager.eventmanager.repositories;

import com.mseventmanager.eventmanager.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {


}
