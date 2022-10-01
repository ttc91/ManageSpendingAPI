package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long accountId;

    @Column(length = 30, nullable = false, unique = true)
    private String accountUsername;

    @Column(nullable = false)
    private String accountPassword;

    @Column(nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedDate;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "account")
    private Set<Wallet> wallets;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "account")
    private Set<Expense> expenses;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "account")
    private Set<Budget> budgets;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "account")
    private Set<Goal> goals;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "account")
    private Set<Event> events;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "account")
    private Set<History> histories;

}
