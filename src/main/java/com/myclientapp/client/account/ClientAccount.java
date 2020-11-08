package com.myclientapp.client.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myclientapp.client.Client;
import com.myclientapp.common.IdEntity;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class ClientAccount extends IdEntity {
    private int accountNumber;
    private double accountBalance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id",
    nullable = false,
    foreignKey = @ForeignKey(
            name = "fk_accounts_clients_id"
    ))
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Client client;

}
