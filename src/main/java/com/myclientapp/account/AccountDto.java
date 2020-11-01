package com.myclientapp.account;

import com.myclientapp.common.AuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends AuditableDto<AccountDto> {
    private int accountNumber;
    private double balance;
}
