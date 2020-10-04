package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long idOrder;
    @Basic
    @Column(name = "producer")
    private long producer;
    @Basic
    @Column(name = "product")
    private long product;
    @Basic
    @Column(name = "order_time")
    private Timestamp orderTime;
    @Basic
    @Column(name = "customer")
    private long customer;
    @Basic
    @Column(name = "status")
    private long status;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "description")
    private Long description;

    public Orders(Orders orders) {
        this.idOrder = orders.idOrder;
        this.producer = orders.producer;
        this.product = orders.product;
        this.orderTime = orders.orderTime;
        this.customer = orders.customer;
        this.status = orders.status;
        this.quantity = orders.quantity;
        this.description = orders.description;
    }
}
