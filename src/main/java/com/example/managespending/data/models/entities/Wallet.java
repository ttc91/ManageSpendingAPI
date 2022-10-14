package com.example.managespending.data.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;

    @Column(length = 100, nullable = false)
    private String walletName;

    @Column(nullable = false)
    private BigDecimal walletBalance;

    @Column
    @CreationTimestamp
    private Date createdDate;

    @Column
    @UpdateTimestamp
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "wallet")
    private List<Event> events;

    @OneToMany(mappedBy = "wallet")
    private List<History> histories;

    @PreRemove
    public void setHistoryNull (){
        this.histories.forEach(h -> h.setWallet(null));
    }

}
