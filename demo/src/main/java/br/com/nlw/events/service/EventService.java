package br.com.nlw.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nlw.events.repo.EventRepo;
import br.com.nlw.events.model.Event;

import java.util.List;

@Service
public class EventService {
    
    @Autowired
    private EventRepo eventRepo;

    public List<Event> getAllEvents() {
        return (List<Event>)eventRepo.findAll();
    };

    public Event getByPrettyName(String prettyName) {
        return eventRepo.findByPrettyName(prettyName);
    };

    public Event addNewEvent(Event event) {
        //gerando o pretty name
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepo.save(event);
    };
}
