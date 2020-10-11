package com.myclientapp.account;

import com.myclientapp.client.Client;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private int accountNumber;
    private double balance;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
