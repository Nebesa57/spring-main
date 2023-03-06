package com.example.kyrsach.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 2, max = 50)
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private User owner;

    public Comments(String text, User author, User owner) {
        this.text = text;
        this.author = author;
        this.owner = owner;
    }
}
