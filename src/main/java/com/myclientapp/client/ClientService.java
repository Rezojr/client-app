package com.myclientapp.client;

import com.myclientapp.account.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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

    Client updateClient(Client newClient, Long id) {

        return clientRepository.findById(id) //
                .map(client -> {
                    client.setFirstName(newClient.getFirstName());
                    client.setLastName(newClient.getLastName());
                    client.setAge(newClient.getAge());
                    client.setEmail(newClient.getEmail());
                    client.setPhoneNumber(newClient.getPhoneNumber());
                    return clientRepository.save(client);
                }).orElseThrow(() -> new ClientNotFoundException(id));
    }

    void delete(Long id) {

        clientRepository.deleteById(id);
    }

}
