package com.myclientapp.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Auditable<String> {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Min(value = 18, message = "Age should be above than 18 years")
    @Max(value = 150, message = "Age should be less than 150 years")
    private int age;

    @Email
    private String email;
    private String phoneNumber;
    private int accountNumber;

}
