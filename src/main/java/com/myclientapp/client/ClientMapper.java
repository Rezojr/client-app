package com.myclientapp.client;

import com.myclientapp.client.Client;
import com.myclientapp.client.ClientDto;
import com.myclientapp.common.mapper.AbstractMapper;
import com.myclientapp.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface ClientMapper extends AbstractMapper<Client, ClientDto> {
}
