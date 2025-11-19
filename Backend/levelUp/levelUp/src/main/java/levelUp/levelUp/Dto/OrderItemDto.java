package levelUp.levelUp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    
    private Long productId;
    private String productTitle;
    private BigDecimal unitPrice;
    private int quantity;
}