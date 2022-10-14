package com.example.managespending.data.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(length = 100, nullable = false)
    private String eventName;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date eventEndDate;

    @Column(columnDefinition="BOOLEAN DEFAULT FALSE NOT NULL")
    private Boolean eventStatus;

    @Column(nullable = false)
    private String eventIcon;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonBackReference
    private Wallet wallet;

    @OneToMany(mappedBy = "event")
    private List<History> histories;

    @PreRemove
    public void setHistoryNull (){
        this.histories.forEach(h -> h.setEvent(null));
    }

}
