package payMyServer.QuickTip.Respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import payMyServer.QuickTip.Entity.Tip;

import java.util.List;

public interface TipRepo extends MongoRepository<Tip, String> {
}