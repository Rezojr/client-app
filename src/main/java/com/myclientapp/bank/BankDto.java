package com.myclientapp.bank;

import com.myclientapp.common.IdDto;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BankDto extends IdDto {

    private String bankName;
    private int bankBalance;

}
