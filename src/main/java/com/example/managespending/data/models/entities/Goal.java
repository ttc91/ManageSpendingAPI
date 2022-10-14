package com.example.managespending.data.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "goal")
public class Goal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long goalId;

    @Column(length = 30, nullable = false, unique = true)
    private String goalName;

    @Column(nullable = false)
    private Boolean goalStatus;

    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0 NOT NULL")
    private BigDecimal goalPresentCost;

    @Column(nullable = false)
    private BigDecimal goalFinalCost;

    @Column(nullable = false)
    @CreationTimestamp
    private Date goalStartDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date goalEndDate;

    @Column(nullable = false)
    private String goalIcon;

    @Column(nullable = false)
    private String goalColor;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @OneToMany(mappedBy = "goal")
    private List<History> histories;

    @PreRemove
    public void setHistoryNull (){
        this.histories.forEach(h -> h.setGoal(null));
    }

}
