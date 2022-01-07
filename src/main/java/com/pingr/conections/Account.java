package com.pingr.conections;


import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Account {

    @Id
    private Long id;

    @Column(
         name = "username",
         nullable = false,
         unique = true
    )
    private String name;


    @ManyToMany
    private Set<Account> friends = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Set<Account> getFriends() {
        
        return friends;
    }

    public void setFriends(Set<Account> friends) {
        this.friends = friends;
    }
}
