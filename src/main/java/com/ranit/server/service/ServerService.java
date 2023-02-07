package com.ranit.server.service;

import com.ranit.server.enumeration.Status;
import com.ranit.server.model.Server;
import com.ranit.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

public abstract class ServerService {
    public abstract Server createServer(Server server);
    public abstract Server pingServer(String ipAddress);
    public abstract Collection<Server> getServerList(int pageLimit);
    public abstract Server getServer(Long id);
    public abstract Server updateServer(Server server);
    public abstract Boolean deleteServer(Long id);
}

@Service
@RequiredArgsConstructor    // Handles DI for 'serverRepository' by itself instead of @Autowired
@Transactional
@Slf4j                      // For Logging
class ServerServiceImpl extends ServerService {
    private final ServerRepository serverRepository;

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
    public Collection<Server> getServerList(int pageLimit) {
        return null;
    }

    @Override
    public Server getServer(Long id) {
        return null;
    }

    @Override
    public Server updateServer(Server server) {
        return null;
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
