package org.bsuir.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long idUser;
    private String name;
    private String surname;
    private String email;
    private String password;
    private long role;

    public void forgetPassword(){
        password ="";
    }
}
