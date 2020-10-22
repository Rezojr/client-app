package com.myclientapp.common.mapper;

import com.myclientapp.common.IdEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BaseDtoConverter {

    @PersistenceContext
    private EntityManager entityManager;

    public BaseDto toBaseDto(IdEntity idEntity) {
        return BaseDto.from(idEntity.getId());
    }

    @Named("fromDatabase")
    public <T extends IdEntity> T fromBase(BaseDto baseDto, @TargetType Class<T> entityClazz) {
        return Optional.ofNullable(baseDto)
                .map(BaseDto::getId)
                .map(id -> entityManager.find(entityClazz, id))
                .orElse(null);
    }

}
