package com.example.managespending.data.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long roleId;

    @Column(length = 15, nullable = false, unique = true)
    private String roleName;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "role")
    private List<Account> accounts;

}
