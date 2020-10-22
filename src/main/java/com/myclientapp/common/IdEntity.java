package com.myclientapp.common;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class IdEntity {

    @Id
    @GeneratedValue
    private Long id;

}
