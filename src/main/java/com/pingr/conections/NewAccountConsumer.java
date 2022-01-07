package com.pingr.conections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NewAccountConsumer {
    private final AccountService service;

    @Autowired
    public NewAccountConsumer(AccountService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "${topic.accounts}",
            groupId = "connection_new_accounts"
    )
    public void consume(Account account) throws IOException {
        this.service.storeAccount(account);
    }


    @KafkaListener(
            topics = "${topic.account-updated}"
    )
    public void update(Account account) throws IOException {
        this.service.storeAccount(account);
    }





}
