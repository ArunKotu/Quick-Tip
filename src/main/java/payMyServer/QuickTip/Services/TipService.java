package payMyServer.QuickTip.Services;

import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payMyServer.QuickTip.Entity.Tip;
import payMyServer.QuickTip.Respository.TipRepo;
import java.util.Optional;

@Service
public class TipService {

    @Autowired
    TipRepo tipRepository;

    @Autowired
    RazorpayService razorpayService;

    public ResponseEntity<Tip> getTipById(String id) {
        Optional<Tip> tipOpt = tipRepository.findById(id);
        if (tipOpt.isPresent()) {
            return ResponseEntity.ok(tipOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Tip> addTip(Tip tip) {
        tip.setPaymentStatus("PENDING");
        try {
            Order order = razorpayService.createOrder(tip.getAmount());
            tip.setRazorpayOrderId(order.get("id"));
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Or handle with custom error
        }
        Tip saved = tipRepository.save(tip);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<Tip> updateTipStatus(String id, String status) {
        Optional<Tip> tipOpt = tipRepository.findById(id);
        if (tipOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tip tip = tipOpt.get();
        if (!"PENDING".equals(tip.getPaymentStatus())) {
            return ResponseEntity.badRequest().body(null);
        }
        tip.setPaymentStatus(status);
        Tip updated = tipRepository.save(tip);
        return ResponseEntity.ok(updated);
    }
}