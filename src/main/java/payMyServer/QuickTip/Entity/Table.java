package payMyServer.QuickTip.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tables")
public class Table {
    @Id
    private String id;
    private int number;
    private String qrCodeUrl;
}
