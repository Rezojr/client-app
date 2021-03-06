package com.myclientapp.bank;

import com.myclientapp.client.Client;
import com.myclientapp.common.IdEntity;
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
public class Bank extends IdEntity {

    private String bankName;
    private int bankBalance;

}
