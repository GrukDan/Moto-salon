package org.bsuir.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_type", schema = "motosalon")
@Data
public class ProductType {
    @Id
    @Column(name = "id_product_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductType;
    @Basic
    @Column(name = "product_type")
    private String productType;
}
