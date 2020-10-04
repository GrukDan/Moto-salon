package org.bsuir.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bsuir.model.Order;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class OrderDto extends Order {
    //extra data
    private String productName;
    private long idProductType;
    private String productTypeName;
    private String statusName;
    private String name;
    private String surname;
    private String producerName;
    private String descriptionData;
}
