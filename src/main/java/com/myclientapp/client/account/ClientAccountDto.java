package com.myclientapp.client.account;

import com.myclientapp.common.IdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientAccountDto extends IdDto {
    private int accountNumber;
    private double accountBalance;
}
