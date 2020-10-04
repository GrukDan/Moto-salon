package org.bsuir.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bsuir.model.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class OrdersDto extends Orders {
    //extra data
    private String productName;
    private long idProductType;
    private String productTypeName;
    private String statusName;
    private String name;
    private String surname;
    private String producerName;
    private String descriptionData;

    @Builder(builderMethodName = "orderDtoBuilder")
    public OrdersDto(Long idOrder, long producer, long product, Timestamp orderTime, long customer, long status, int quantity, Long description, String productName, long idProductType, String productTypeName, String statusName, String name, String surname, String producerName, String descriptionData) {
        super(idOrder, producer, product, orderTime, customer, status, quantity, description);
        this.productName = productName;
        this.idProductType = idProductType;
        this.productTypeName = productTypeName;
        this.statusName = statusName;
        this.name = name;
        this.surname = surname;
        this.producerName = producerName;
        this.descriptionData = descriptionData;
    }

    public OrdersDto(Orders orders) {
        super(orders);
    }

    public Orders createOrder() {
        return Orders.builder()
                .idOrder(getIdOrder())
                .producer(getProducer())
                .product(getProduct())
                .orderTime(getOrderTime())
                .customer(getCustomer())
                .status(getStatus())
                .quantity(getQuantity())
                .description(getDescription())
                .build();
    }
}
