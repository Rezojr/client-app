package com.myclientapp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Client create(Client newClient) {

        return clientRepository.save(newClient);
    }

    public Client update(Client newClient, Long id) {

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

    public void delete(Long id) {

        clientRepository.deleteById(id);
    }

}
