package com.ranit.server.repository;

import com.ranit.server.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    /**
     * Naming convention for the functions are extremely important.
     * We are searching via ipAddress since it's 'Unique'.
     * For instance,
     *      'findBy' -> Tells JPA to search for a 'Server'
     *      'IpAddress' will then be checked against the supplied 'ipAddress'
     */
    Server findByIpAddress(String ipAddress);
}
