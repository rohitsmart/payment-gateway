package com.payment.gateway.payment;


import com.payment.gateway.entity.User;
import com.payment.gateway.security.UserDetail;
import com.payment.gateway.user.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/payment")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "APIs related to payment")
public class PaymentController {

    private final UserDetail userDetail;

    @Autowired
    private final PaymentService paymentService;


    @PostMapping("/order-details")
    @Operation(summary = "Fetch the order details")
    public ResponseEntity<OrderResponse> fetchOrderDetails(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(paymentService.fetchOrderDetails(orderRequest));
    }

    @GetMapping("/order-details")
    @Operation(summary = "Fetch the order details")
    public ResponseEntity<OrderResponse> fetchOrderDetailsByTID(@RequestParam String transactionID) {
        return ResponseEntity.ok(paymentService.fetchOrderDetailsByTID(transactionID));
    }

}
