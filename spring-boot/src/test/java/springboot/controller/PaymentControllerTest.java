package springboot.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springboot.Cart;
import springboot.service.PaymentService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubmitOrder_Success() throws Exception {
        Cart cart = new Cart(); // Assuming cart is properly instantiated and populated
        doNothing().when(paymentService).checkout(cart);

        ResponseEntity<String> response = paymentController.submitOrder(cart);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order has been successfully processed.", response.getBody());

        verify(paymentService, times(1)).checkout(cart);
    }

    @Test
    public void testSubmitOrder_Failure() throws Exception {
        Cart cart = new Cart(); // Assuming cart is properly instantiated and populated
        doThrow(new RuntimeException("Checkout failed")).when(paymentService).checkout(cart);

        ResponseEntity<String> response = paymentController.submitOrder(cart);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to process order: Checkout failed", response.getBody());

        verify(paymentService, times(1)).checkout(cart);
    }
}
