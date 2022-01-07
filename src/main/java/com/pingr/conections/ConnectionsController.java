package com.pingr.conections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "/connections")
public class ConnectionsController {

    private final AccountService accountService;

    @Autowired
    public ConnectionsController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping(path = "/{aid}/{bid}")
    public boolean addFriends(@PathVariable("aid") Long aid, @PathVariable("bid") Long bid) {
        return this.accountService.stablishFriendshipBetween(aid, bid);
    }


    @GetMapping(path = "/{id}/count")
    public Integer countFollowers(@PathVariable("id") Long id) {
        return this.accountService.getCount(id);
    }

    @GetMapping(path = "/getFriendship/{id}")
    public Set<Account> getFriendShip(@PathVariable("id") Long id) {
        return this.accountService.getAllFriends(id);
    }

    @DeleteMapping(path = "/{aid}/{bid}")
    public boolean deleteConnection(@PathVariable("aid") Long aid, @PathVariable("bid") Long bid) {
        return this.accountService.unstablishConnection(aid, bid);
    }


}
