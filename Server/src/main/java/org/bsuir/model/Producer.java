package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producer")
    private Long idProducer;
    @Basic
    @Column(name = "producer_name")
    private String producerName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "telephone_number")
    private String telephoneNumber;
}
