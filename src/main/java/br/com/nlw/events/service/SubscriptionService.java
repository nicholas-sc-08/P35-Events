package br.com.nlw.events.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nlw.events.controller.exception.EventNotFoundException;
import br.com.nlw.events.controller.exception.SubscriptionConflictException;
import br.com.nlw.events.controller.exception.UserIndicatorNotFoundException;
import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.dto.SubscriptionRankingByUser;
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

    
    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        
        Event event = eventRepo.findByPrettyName(eventName);
        if(event == null) {
            throw new EventNotFoundException("Event "+eventName+" not found");
        }
        User existingUser = userRepo.findByEmail(user.getEmail());
        if(existingUser == null) {
            existingUser = userRepo.save(user);
        }
        User indicator = null;
        if(userId != null) {
            
            indicator = userRepo.findById(userId).orElse(null);
            if(indicator == null) {
                throw new UserIndicatorNotFoundException("User "+userId+" indicator don't exist!");
            }
        }
        
        Subscription subs = new Subscription();
        subs.setEvent(event);
        subs.setSubscriber(existingUser);
        subs.setIndication(indicator);

        Subscription tmpSub = subscriptionRepo.findByEventAndSubscriber(event, existingUser);
        if(tmpSub != null) {
            throw new SubscriptionConflictException("Already exists subscription to user "+existingUser.getName()+" to the event "+event.getTitle());
        }
        
        Subscription res = subscriptionRepo.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {

        Event event = eventRepo.findByPrettyName(prettyName);
        if(event == null) {
            throw new EventNotFoundException("Event ranking "+prettyName+" does not exist");
        }
        return subscriptionRepo.generateRanking(event.getEventId());
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId) {
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);

        SubscriptionRankingItem item = ranking.stream().filter(i -> i.userId().equals(userId)).findFirst().orElse(null);
        if(item == null) {
            throw new UserIndicatorNotFoundException("Don't have subscriptions with indicators to user "+userId);
        }

        Integer position = IntStream.range(0, ranking.size()).filter(pos -> ranking.get(pos).userId().equals(userId)).findFirst().getAsInt();

        return new SubscriptionRankingByUser(item, position + 1);
    }
}
