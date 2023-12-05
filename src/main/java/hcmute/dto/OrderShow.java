package hcmute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import hcmute.model.order.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderShow {
    private Order order;
    private String username;
    private String orderDate;
}
