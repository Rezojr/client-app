package com.myclientapp.client;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final ClientModelAssembler assembler;

    public ClientService(ClientRepository repository, ClientModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    Client one(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    List<EntityModel<Client>> all() {

        return repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());
    }


    ResponseEntity<?> newClient(Client newClient) {

        EntityModel<Client> entityModel = assembler.toModel(repository.save(newClient));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    ResponseEntity<?> replaceClient(Client newClient, Long id) {

        Client updatedClient = repository.findById(id) //
                .map(client -> {
                    client.setFirstName(newClient.getFirstName());
                    client.setLastName(newClient.getLastName());
                    client.setAge(newClient.getAge());
                    client.setEmail(newClient.getEmail());
                    client.setPhoneNumber(newClient.getPhoneNumber());
                    client.setAccountNumber(newClient.getAccountNumber());
                    return repository.save(client);
                }) //
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });

        EntityModel<Client> entityModel = assembler.toModel(updatedClient);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    ResponseEntity<?> deleteClient(Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
