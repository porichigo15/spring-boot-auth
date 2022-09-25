package com.example.assignmentapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "PARTY_GROUP")
@Entity
public class PartyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER")
    private User user;

    @ManyToOne
    @JoinColumn(name = "PARTY")
    private Party party;
}
