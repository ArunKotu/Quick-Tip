package payMyServer.QuickTip.Services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import payMyServer.QuickTip.Entity.PaymentVerificationRequest;

import java.util.logging.Logger;

@Service
public class RazorpayService {
    private static final Logger LOGGER = Logger.getLogger(RazorpayService.class.getName());
    private RazorpayClient client;
    private String secret;

    public RazorpayService(@Value("${razorpay.key}") String key,
                           @Value("${razorpay.secret}") String secret) throws RazorpayException {
        this.secret = secret;
        try {
            this.client = new RazorpayClient(key, secret);
            LOGGER.info("Razorpay client initialized with key: " + key);
        } catch (RazorpayException e) {
            LOGGER.severe("Failed to initialize Razorpay client: " + e.getMessage());
            throw e;
        }
    }

    public Order createOrder(double amount) throws RazorpayException {
        LOGGER.info("Creating order for amount: " + amount);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) (amount * 100));
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);
        Order order = client.Orders.create(orderRequest);
        LOGGER.info("Order created with ID: " + order.get("id"));
        return order;
    }

    public boolean verifyPaymentSignature(PaymentVerificationRequest request) {
        try {
            JSONObject attributes = new JSONObject();
            attributes.put("razorpay_order_id", request.getRazorpayOrderId());
            attributes.put("razorpay_payment_id", request.getRazorpayPaymentId());
            attributes.put("razorpay_signature", request.getRazorpaySignature());

            Utils.verifyPaymentSignature(attributes, secret);
            LOGGER.info("Payment signature verified for order: " + request.getRazorpayOrderId());
            return true;
        } catch (RazorpayException e) {
            LOGGER.warning("Payment signature verification failed: " + e.getMessage());
            return false;
        }
    }
}