package com.myclientapp.client.account;

import com.myclientapp.common.mapper.AbstractMapper;
import com.myclientapp.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ClientAccountMapper extends AbstractMapper<ClientAccount, ClientAccountDto> {
}
