package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Description {
    private Long idDescription;
    private String description;

    public Description(String description){
        this.description = description;
    }
}
