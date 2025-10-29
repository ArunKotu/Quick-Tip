package payMyServer.QuickTip.Controllers;

import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import payMyServer.QuickTip.Entity.Tip;
import payMyServer.QuickTip.Respository.TipRepo;
import payMyServer.QuickTip.Services.RazorpayService;

import java.util.Date;
import java.util.logging.Logger;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tips")
public class TipController {
    private static final Logger LOGGER = Logger.getLogger(TipController.class.getName());

    @Autowired
    private TipRepo tipRepo;

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping
    public ResponseEntity<Tip> createTip(@RequestBody Tip tip) {
        try {
            LOGGER.info("Received tip request: " + tip);
            Order order = razorpayService.createOrder(tip.getAmount());
            String razorpayOrderId = order.get("id").toString();

            tip.setRazorpayOrderId(razorpayOrderId);
            tip.setCreatedAt(new Date());
            tip.setTableId(tip.getTableId());
            tip.setServerId(tip.getServerId());
            Tip savedTip = tipRepo.save(tip);

            LOGGER.info("Saved tip with orderId: " + razorpayOrderId);
            return ResponseEntity.ok(savedTip);
        } catch (Exception e) {
            LOGGER.severe("Error creating tip: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateTipStatus(@PathVariable String id, @RequestParam String status) {
        var tipOpt = tipRepo.findById(id);
        if (tipOpt.isPresent()) {
            Tip tip = tipOpt.get();
            tip.setStatus(status);
            tipRepo.save(tip);
            return ResponseEntity.ok("Status updated to " + status);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}