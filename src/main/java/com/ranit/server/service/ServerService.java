package com.ranit.server.service;

import com.ranit.server.enumeration.Status;
import com.ranit.server.model.Server;
import com.ranit.server.repository.ServerRepository;
import com.ranit.server.utils.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;

public abstract class ServerService {
    public abstract Server createServer(Server server);
    public abstract Server pingServer(String ipAddress);
    public abstract Collection<Server> getServerList(int pageNumber, int pageLimit);
    public abstract Server getServer(Long id);
    public abstract Server updateServer(Server server);
    public abstract void updateServerIpAddress(Long id, String ipAddress);
    public abstract Boolean deleteServer(Long id);
}

@Service
@RequiredArgsConstructor    // Handles DI for 'serverRepository' by itself instead of @Autowired
@Transactional
@Slf4j                      // For Logging
class ServerServiceImpl extends ServerService {
    private final ServerRepository serverRepository;
    private final Validation validation;

    @Override
    public Server createServer(Server server) {
        log.info("Saving new Server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepository.save(server);
    }

    @Override
    public Server pingServer(String ipAddress) {
        log.info("Pinging Server IP: {}", ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        try {
            server = pingServerUsingIp(server, ipAddress);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Exception occurred while Pinging Server with IP: "+ipAddress
            );
        }
        return serverRepository.save(server);
    }

    @Override
    public Collection<Server> getServerList(int pageNumber, int pageLimit) {
        log.info("Fetching first "+pageLimit+" Servers");
        return serverRepository.findAll(
                PageRequest.of(pageNumber, pageLimit)
        ).toList();
    }

    @Override
    public Server getServer(Long id) {
        log.info("Getting Server with id: {}", id);
        Optional<Server> optionalServer = serverRepository.findById(id);
        if (optionalServer.isPresent()) {
            return optionalServer.get();
        } else {
            throw new IllegalStateException(
                    "Server with id: "+id+" does not exist"
            );
        }
    }

    @Override
    public Server updateServer(Server server) {
        return null;
    }

    @Transactional
    @Override
    public void updateServerIpAddress(Long id, String ipAddress) {
        log.info("Updating server with ipAddress: {}", ipAddress);
        Server curServer = serverRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Server with id: "+id+" does not exist")
                );

        if (validation.isIpAddressValid(ipAddress, curServer)) {
            // Update ipAddress using Setter [required when using @Transactional]
            curServer.setIpAddress(ipAddress);
        }
    }

    @Override
    public Boolean deleteServer(Long id) {
        return null;
    }

    private String setServerImageUrl() {
        return null;
    }

    /**
     * Pings the server based on the ipAddress using InetAddress
     * @param server - Server model
     * @param ipAddress - IP address to be pinged
     * @return server with updated 'Status'
     */
    private Server pingServerUsingIp(Server server, String ipAddress)
            throws IOException {
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(
                address.isReachable(10000) ?
                        Status.SERVER_UP : Status.SERVER_DOWN
        );
        return server;
    }
}
