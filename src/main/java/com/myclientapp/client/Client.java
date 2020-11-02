package com.myclientapp.client;

import com.myclientapp.common.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client extends IdEntity {

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

}
