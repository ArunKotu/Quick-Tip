package payMyServer.QuickTip.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Data
@Document(collection = "tip")
public class Tip {
    @Id
    private String id;
    private String tableId;
    private String serverId;
    private double amount;
    private String razorpayOrderId;
    private Date createdAt;
    private String status;


    public void setPaymentStatus(String newStatus) {
        status = newStatus;
    }

    public String getPaymentStatus() {
        return status;
    }
}