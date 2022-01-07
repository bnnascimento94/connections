package com.pingr.conections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProducerService {

    @Value(value = "${topic.following}")
    private String following;

    @Value(value = "${topic.unfollowing}")
    private String unfollowing;

    @Autowired
    private KafkaTemplate<String, Object> template;


    public void onNewFollowing(List<Account> accounts){
        this.template.send(this.following, accounts);
    }

    public void onUnfollow(List<Account> accounts){
        this.template.send(this.unfollowing, accounts);
    }


}
