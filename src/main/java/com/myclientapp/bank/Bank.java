package com.myclientapp.bank;

import com.myclientapp.client.Client;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Null;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    @Id
    @GeneratedValue
    private Long id;

    private String bankName;
    private int bankBalance;

    //@Nullable
    //private List<Client> clients;

}
