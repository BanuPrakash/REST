package com.adobe.r2app;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
@Table(name="USER_ENTITY")
public class UserEntity {
    @Id
    @Column(name="FIRST_NAME")
    String firstName;
    @Column(name="LAST_NAME")
    String lastName;
    String address;

    public UserEntity(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
