package com.myclientapp.bank;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    public void shouldReturnFindAl() {
        List<Bank> bankList = new ArrayList();
        bankList.add(new Bank(1L, "PKO", 10000));
        bankList.add(new Bank(2L, "Test", 20000));

        given(bankRepository.findAll()).willReturn(bankList);

        List<Bank> expected = bankService.getAll();

        assertEquals(expected, bankList);
    }

    @Test
    public void findBankById() {
        final Long id = 1L;
        final Bank bank = new Bank(1L, "PKO", 10000);

        given(bankRepository.findById(id)).willReturn(Optional.of(bank));

        final Bank expected = bankService.findById(id);

        System.out.println(bankService.findById(id));

        assertThat(expected).isNotNull();
        assertEquals(expected, bank);
    }

    @Test
    public void shouldBeDeleted() {
        final Long id = 1L;

        bankService.deleteBank(id);
        bankService.deleteBank(id);

        verify(bankRepository, times(2)).deleteById(id);
    }

    @Test
    public void updateBank() {
        final Bank bank = new Bank(1L, "PKO", 10000);
        given(bankRepository.save(bank)).willReturn(bank);

        final Bank expected = bankService.updateBank(bank, 1L);
        assertThat(expected).isNotNull();

        verify(bankRepository).save(any(Bank.class));
    }

    @Test
    public void createBankTest() {
        final Bank bank = new Bank(null, "PKO", 10000);

        given(bankRepository.save(bank)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Bank savedBank = bankService.newBank(bank);
        assertThat(savedBank).isNotNull();
        System.out.println(savedBank);

        verify(bankRepository).save(any(Bank.class));
    }

}
