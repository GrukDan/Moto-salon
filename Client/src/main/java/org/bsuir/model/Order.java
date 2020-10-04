package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long idOrder;
    private long producer;
    private long product;
    private Timestamp orderTime;
    private long customer;
    private long status;
    private int quantity;
    private Long description;
}

