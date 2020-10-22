package com.myclientapp.bank;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    public void shouldReturnFindAll() {
        List<Bank> bankList = new ArrayList();
        bankList.add(new Bank("PKO", 10000));
        bankList.add(new Bank("Test", 20000));
        Page<Bank> bankListPage = new PageImpl<>(bankList);

        Pageable pageable = PageRequest.of(0, 2);

        when(bankRepository.findAll(pageable)).thenReturn(bankListPage);
        Page<Bank> expected = bankService.findAllBanks(pageable);

        assertEquals(expected.getTotalElements(), bankListPage.getTotalElements());
    }

    @Test
    public void findBankById() {
        final Long id = 1L;
        final Bank bank = new Bank("PKO", 10000);
        given(bankRepository.findById(id)).willReturn(Optional.of(bank));
        final Bank expected = bankService.findBankById(id);
        assertThat(expected).isNotNull();
        assertEquals(expected, bank);
    }

    @Test
    public void shouldBeDeleted() {
        final Long id = 1L;
        bankService.delete(id);
        verify(bankRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void updateBank() {
        final Long id = 1L;
        final Bank bank = new Bank("PKO", 10000);
        given(bankRepository.save(bank)).willReturn(bank);
        given(bankRepository.findById(id)).willReturn(Optional.of(bank));
        final Bank expected = bankService.updateBank(bank, id);
        assertThat(expected).isNotNull();
        verify(bankRepository).save(any(Bank.class));
    }

    @Test(expected = BankNotFoundException.class)
    public void shouldThrowExceptionIfBankNotFound() {
        Mockito.when(bankRepository.findById(anyLong())).thenThrow(new BankNotFoundException(1L));

        bankService.updateBank(new Bank(), 1L);

    }

    @Test
    public void shouldCreateNewBank() {
        final Bank bank = new Bank("PKO", 10000);
        given(bankRepository.save(bank)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Bank savedBank = bankService.createBank(bank);
        assertThat(savedBank).isNotNull();
        verify(bankRepository).save(any(Bank.class));
    }

}
