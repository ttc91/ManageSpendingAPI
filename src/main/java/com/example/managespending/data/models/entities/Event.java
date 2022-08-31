package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name", length = 100, unique = true, nullable = false)
    @NotNull(message = "Please input correct value of event name !!!")
    private String eventName;

    @Column(name = "event_start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Please input start date !!!")
    private Date startDate;

    @Column(name = "event_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "event_status", columnDefinition="BOOLEAN DEFAULT FALSE NOT NULL")
    private boolean eventStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id", nullable = false, referencedColumnName = "transaction_id")
    @NotNull(message = "Please input your transaction !!!")
    private Transaction transaction;

}
