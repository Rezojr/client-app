package com.myclientapp.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private String email;

}
