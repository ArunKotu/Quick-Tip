package payMyServer.QuickTip.Controllers;

import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import payMyServer.QuickTip.Services.RazorpayService;

@RestController
public class PaymentTestController {

    @Autowired
    private RazorpayService razorpayService;

    @GetMapping("/test/create-order")
    public ResponseEntity<?> createOrder(@RequestParam double amount) {
        try {
            Order order = razorpayService.createOrder(amount);
            return ResponseEntity.ok(order.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }
}