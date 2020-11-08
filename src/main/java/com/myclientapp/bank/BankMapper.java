package com.myclientapp.bank;

import com.myclientapp.bank.Bank;
import com.myclientapp.bank.BankDto;
import com.myclientapp.common.mapper.AbstractMapper;
import com.myclientapp.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface BankMapper extends AbstractMapper<Bank, BankDto> {
}
