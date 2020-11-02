package com.myclientapp.client;

import com.myclientapp.common.IdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto extends IdDto {

    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;

}
