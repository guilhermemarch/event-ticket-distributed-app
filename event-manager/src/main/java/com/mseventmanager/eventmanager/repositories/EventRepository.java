package com.mseventmanager.eventmanager.repositories;

import com.mseventmanager.eventmanager.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {


    List<Event> findAllByOrderByEventNameAsc();
}
