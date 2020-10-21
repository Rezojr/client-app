package com.myclientapp.bank.dto;

import com.myclientapp.common.AuditableDto;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BankDto extends AuditableDto<BankDto> {

    private String bankName;
    private int bankBalance;

}
