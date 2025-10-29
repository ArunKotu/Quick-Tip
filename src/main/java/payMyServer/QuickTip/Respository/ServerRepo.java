package payMyServer.QuickTip.Respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import payMyServer.QuickTip.Entity.Server;

import java.util.List;

public interface ServerRepo extends MongoRepository<Server, String> {
    List<Server> findByActiveTrue();
}