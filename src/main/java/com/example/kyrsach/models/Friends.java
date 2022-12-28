package com.example.kyrsach.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Friends {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "youStr_id")
    private User youStr;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friends_id")
    private User friends;

    public Friends(User youStr, User friends) {
        this.youStr = youStr;
        this.friends = friends;
    }
}
