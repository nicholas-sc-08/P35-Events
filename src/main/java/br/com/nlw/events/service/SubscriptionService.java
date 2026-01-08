package br.com.nlw.events.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.controller.exception.EventNotFoundException;
import br.com.nlw.events.controller.exception.SubscriptionConflictException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repo.EventRepo;
import br.com.nlw.events.repo.SubscriptionRepo;
import br.com.nlw.events.repo.UserRepo;

@Service
public class SubscriptionService {
    
    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    
    public Subscription createNewSubscription(String eventName, User user) {
        
        Event event = eventRepo.findByPrettyName(eventName);
        if(event == null) {
            throw new EventNotFoundException("Event "+eventName+" not found");
        }
        User existingUser = userRepo.findByEmail(user.getEmail());
        if(existingUser == null) {
            existingUser = userRepo.save(user);
        }
        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(existingUser);

        Subscription tmpSub = subscriptionRepo.findByEventAndSubscriber(event, existingUser);
        if(tmpSub != null) {
            throw new SubscriptionConflictException("Already exists subscription to user "+existingUser.getName()+" to the event "+event.getTitle());
        }
        
        Subscription res = subscriptionRepo.save(subs);
        return res;
    }
}
