package com.myclientapp.client.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAccountRepository extends JpaRepository<ClientAccount, Long> {

    Page<ClientAccount> findAllByClientId(Long clientId, Pageable pageable);

}
