package payMyServer.QuickTip.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "server")
public class Server {
    @Id
    private String id;
    private String name;
    private String upiId;
    private boolean active;
    private String tableId;
}
