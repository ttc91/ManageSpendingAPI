package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long eventId;

    @Column(length = 100, unique = true, nullable = false)
    private String eventName;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date eventStartDate;

    @Column(columnDefinition="BOOLEAN DEFAULT FALSE NOT NULL")
    private int eventStatus;

    @Column(nullable = false)
    private String eventIcon;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<History> histories;

}
