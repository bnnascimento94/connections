package com.pingr.conections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository repo;
    private ProducerService producerService;

    @Autowired
    public AccountService(AccountRepository repo, ProducerService producerService) {
        this.repo = repo;
        this.producerService = producerService;
    }

    public void storeAccount(Account account) {
        this.repo.save(account);
        System.out.println("salvei a conta:");
        System.out.println(account);
    }


    public void updateAccount(Account account) {
        Account newAccount = this.repo.save(account);
        System.out.println("atualizei a conta:");
        System.out.println(newAccount);
    }

    public void deleteAccount(Long id) {
        Optional<Account> account = this.repo.findById(id);

        if(account.isPresent()){
            this.repo.delete(account.get());
            System.out.println("deletei a conta:");
            System.out.println(account.get());
        }

    }


    public Integer getCount(Long id){
        Optional<Account> account = this.repo.findById(id);

        if(account.isPresent()){
            return account.get().getFriends().size();
        }else{
            return 0;
        }
    }

    public Set<Account> getAllFriends(Long id){
        Optional<Account> account = this.repo.findById(id);

        if(account.isPresent()){
            return account.get().getFriends();
        }else{
            return new HashSet<>();
        }
    }


    public boolean stablishFriendshipBetween(Long aId, Long bId) {
        Optional<Account> aOptional = this.repo.findById(aId);
        Optional<Account> bOptional = this.repo.findById(bId);

        if (!aOptional.isPresent() || !bOptional.isPresent()) return false;

        Account a = aOptional.get();
        Account b = bOptional.get();

        Set<Account> aFriends = a.getFriends();
        aFriends.add(b);
        a.setFriends(aFriends);

        Set<Account> bFriends = b.getFriends();
        bFriends.add(a);
        b.setFriends(bFriends);

        ArrayList<Account> accounts = (ArrayList<Account>) List.of(a, b);

        this.repo.saveAll(accounts);

        this.producerService.onNewFollowing(accounts);

        return true;
    }

    public boolean unstablishConnection(Long aId, Long bId){
        Optional<Account> aOptional = this.repo.findById(aId);
        Optional<Account> bOptional = this.repo.findById(bId);

        if (!aOptional.isPresent() || !bOptional.isPresent()) return false;

        Account a = aOptional.get();
        Account b = bOptional.get();

        a.getFriends().removeIf(account -> account.getId().equals(b.getId()));

        b.getFriends().removeIf(account -> account.getId().equals(a.getId()));


        ArrayList<Account> accounts = (ArrayList<Account>) List.of(a, b);

        this.repo.saveAll(accounts);

        this.producerService.onUnfollow(List.of(a, b));

        return true;


    }


}
