package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;
    @Basic
    @Column(name = "product_name")
    private String productName;
    @Basic
    @Column(name = "product_code")
    private String productCode;
    @Basic
    @Column(name = "product_type")
    private long productType;
    @Basic
    @Column(name = "prise")
    private Double prise;
}
