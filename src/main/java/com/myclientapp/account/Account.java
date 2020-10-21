package com.myclientapp.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myclientapp.client.Client;
import com.myclientapp.common.IdEntity;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account extends IdEntity<Account> {
    private int accountNumber;
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id",
    nullable = false,
    foreignKey = @ForeignKey(
            name = "fk_accounts_clients_id"
    ))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;

}
