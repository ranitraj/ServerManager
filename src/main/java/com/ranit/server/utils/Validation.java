package com.ranit.server.utils;

import com.ranit.server.model.Server;
import com.ranit.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Validation {
    private final ServerRepository serverRepository;

    public boolean isIpAddressValid(String ipAddress, Server server)
            throws IllegalStateException {
        // Validation
        if (ipAddress != null
                && !ipAddress.isEmpty()
                && !Objects.equals(server.getIpAddress(), ipAddress)) {
            // Check if new IP address is unique
            Optional<Server> optionalServer = Optional.ofNullable(
                    serverRepository.findByIpAddress(ipAddress));
            return optionalServer.isEmpty();
        }
        return false;
    }
}
