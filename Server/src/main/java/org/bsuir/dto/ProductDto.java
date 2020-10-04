package org.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bsuir.model.Product;
import org.bsuir.model.ProductType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long idProduct;
    private String productName;
    private String productCode;
    private long productType;
    private Double prise;
    private String productTypeName;

    public static ProductDto of(Product product,String productTypeName){
        return  ProductDto.builder()
                .idProduct(product.getIdProduct())
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .prise(product.getPrise())
                .productType(product.getProductType())
                .productTypeName(productTypeName)
                .build();
    }

    public Product createProduct(){
        return  Product.builder()
                .idProduct(idProduct)
                .productName(productName)
                .productCode(productCode)
                .prise(prise)
                .productType(productType)
                .build();
    }
}
