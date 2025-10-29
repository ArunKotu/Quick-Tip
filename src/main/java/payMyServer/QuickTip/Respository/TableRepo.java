package payMyServer.QuickTip.Respository;

import org.springframework.data.mongodb.repository.MongoRepository;
import payMyServer.QuickTip.Entity.Table;

public interface TableRepo extends MongoRepository<Table,String> {

}