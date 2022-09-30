package com.example.managespending.data.models.entities;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

//    @Column(name = "event_name", length = 100, unique = true, nullable = false)
//    @NotNull(message = "Please input correct value of event name !!!")
//    private String eventName;
//
//    @Column(name = "event_start_date", nullable = false)
//    @Temporal(TemporalType.DATE)
//    @NotNull(message = "Please input start date !!!")
//    private Date startDate;
//
//    @Column(name = "event_end_date")
//    @Temporal(TemporalType.DATE)
//    private Date endDate;
//
//    @Column(name = "event_status", columnDefinition="INTEGER DEFAULT 0 NOT NULL")
//    private int eventStatus;
//
//    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
//    private Set<Transaction> transaction;

}
