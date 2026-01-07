package br.com.nlw.events.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.nlw.events.service.EventService;
import br.com.nlw.events.model.Event;

import java.util.List;

@RestController
public class EventController {
    
    @Autowired
    private EventService service;

    @GetMapping("/events")
    public List<Event> getAllEvents(){
        return service.getAllEvents();
    };

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName){
        Event event = service.getByPrettyName(prettyName);

        if(event != null){
            return ResponseEntity.status(200).body(event);
        };

        return ResponseEntity.status(404).build();
    };

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent){
        return service.addNewEvent(newEvent);
    };

}
