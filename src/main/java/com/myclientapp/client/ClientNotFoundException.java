package com.myclientapp.client;

public class ClientNotFoundException extends RuntimeException{

    ClientNotFoundException(Long id) {
        super("Could not find client " + id);
    }

}
