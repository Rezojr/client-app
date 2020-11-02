package com.myclientapp.client.account;

public class ClientAccountNotFoundException extends RuntimeException{

    public ClientAccountNotFoundException(Long clientId, Long accountId) {
        super("ClientAccount not found with id " + accountId + " and clientId " + clientId);
    }

}
