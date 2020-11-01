package com.myclientapp.account;

import com.myclientapp.account.Account;
import com.myclientapp.account.AccountDto;
import com.myclientapp.common.mapper.AbstractMapper;
import com.myclientapp.common.mapper.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface AccountMapper extends AbstractMapper<Account, AccountDto> {
}
