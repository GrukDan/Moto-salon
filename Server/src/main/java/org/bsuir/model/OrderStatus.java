package org.bsuir.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "order_status", schema = "motosalon")
@Data
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private Long idStatus;
    @Basic
    @Column(name = "status")
    private String status;
}
