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

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_username", length = 30, nullable = false, unique = true)
    @NotNull(message = "Please input correct value of username !!!")
    private String username;

    @Column(name = "account_password", length = 255, nullable = false)
    @NotNull(message = "Please input correct value of password !!!")
    private String password;

    @Column(name = "account_create_date", nullable = false)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "account_update_date", nullable = false)
    @UpdateTimestamp
    private Date updatedDate;

}
