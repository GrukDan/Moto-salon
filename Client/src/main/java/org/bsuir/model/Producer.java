package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producer {

    private Long idProducer;
    private String producerName;
    private String email;
    private String telephoneNumber;
}
