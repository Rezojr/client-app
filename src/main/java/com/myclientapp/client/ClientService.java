package com.myclientapp.client;

import com.myclientapp.account.Account;
import com.myclientapp.client.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;


    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }


    public Client createClient(Client newClient) {

        return clientRepository.save(newClient);
    }

    Client replaceClient(Client newClient, Long id) {

        return clientRepository.findById(id) //
                .map(client -> {
                    client.setFirstName(newClient.getFirstName());
                    client.setLastName(newClient.getLastName());
                    client.setAge(newClient.getAge());
                    client.setEmail(newClient.getEmail());
                    client.setPhoneNumber(newClient.getPhoneNumber());
                    client.setAccountNumber(newClient.getAccountNumber());
                    return clientRepository.save(client);
                }) //
                .orElseGet(() -> {
                    newClient.setId(id);
                    return clientRepository.save(newClient);
                });
    }

    void deleteClient(Long id) {

        clientRepository.deleteById(id);
    }

}
