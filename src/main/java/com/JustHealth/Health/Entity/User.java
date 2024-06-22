package com.JustHealth.Health.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.IdGeneratorType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "customer_name")
    private String Name;

    @Column(name = "customer_email")
    private String Email;

    @Column(name = "customer_password")
    private String Password;

    @Column(name = "customer_account_balance")
    private Integer customerAccountBalance;

}
