package levelUp.levelUp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    
    private String userEmail;
    private BigDecimal total;
    private String shippingAddress;
    private List<OrderItemDto> items;
}