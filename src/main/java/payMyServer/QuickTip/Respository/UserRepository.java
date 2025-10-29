package payMyServer.QuickTip.Respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import payMyServer.QuickTip.Entity.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}