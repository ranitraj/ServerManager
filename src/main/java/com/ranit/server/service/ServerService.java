package com.ranit.server.service;

import com.ranit.server.model.Server;
import com.ranit.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

public abstract class ServerService {
    public abstract String createServer(Server server);
    public abstract Server pingServer(Server ipAddress);
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
    public String createServer(Server server) {
        return null;
    }

    @Override
    public Server pingServer(Server ipAddress) {
        return null;
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
}
