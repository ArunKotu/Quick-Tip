package payMyServer.QuickTip.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payMyServer.QuickTip.Entity.Server;
import payMyServer.QuickTip.Services.ServerService;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    @Autowired
    ServerService serverService;

    @GetMapping("/{tableId}/active")
    public ResponseEntity<List<Server>> getActiveServers(@PathVariable String tableId){
        return serverService.findActiveServers(tableId);
    }
    @PostMapping("/admin")
    public ResponseEntity<String> postServers(@RequestBody Server server){
        return serverService.addServer(server);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Server> updateServer(@PathVariable("id") String id, @RequestBody Server newServer){
        return serverService.modifyServer(id, newServer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServer(@PathVariable("id") String id){
        return serverService.removeServer(id);
    }
}