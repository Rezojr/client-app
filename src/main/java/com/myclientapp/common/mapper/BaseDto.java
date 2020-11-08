package com.myclientapp.common.mapper;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BaseDto implements Serializable {
    private Long id;

    public static BaseDto from(BaseDto other) {
        return from(other.id);
    }

    public static BaseDto from(Long id) {
        return new BaseDto(id);
    }

    public BaseDto toBaseDto() {
        return new BaseDto(id);
    }
}
