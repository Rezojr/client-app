package com.myclientapp.account.dto;

import com.myclientapp.client.Client;
import com.myclientapp.common.AuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends AuditableDto<AccountDto> {
    private int accountNumber;
    private double balance;
}
