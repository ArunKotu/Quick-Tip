package payMyServer.QuickTip.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payMyServer.QuickTip.Entity.Server;
import payMyServer.QuickTip.Respository.ServerRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    @Autowired
    ServerRepo serverRepo;
    public ResponseEntity<List<Server>> findActiveServers(String tableId) {
        List<Server> servers = serverRepo.findByActiveTrue();
        return ResponseEntity.ok(servers);
    }

    public ResponseEntity<String> addServer(Server server) {
        serverRepo.save(server);
        return ResponseEntity.ok("Saved!");
    }

    public ResponseEntity<Server> modifyServer(String id, Server newServer) {
        Optional<Server> serverfromRepo = serverRepo.findById(id);
        Server server;
        if (serverfromRepo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            server = serverfromRepo.get();
            server.setName(newServer.getName() != null && !newServer.getName().isEmpty() ? newServer.getName() : server.getName());
            server.setUpiId(newServer.getUpiId() != null && !newServer.getUpiId().isEmpty() ? newServer.getUpiId() : server.getUpiId());
            server.setActive(newServer.isActive());
            serverRepo.save(server);
        }
        return ResponseEntity.ok(server);
    }

    public ResponseEntity<String> removeServer(String id) {
        Optional<Server> server = serverRepo.findById(id);
        if(server.isPresent()){
            serverRepo.delete(server.get());
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}
