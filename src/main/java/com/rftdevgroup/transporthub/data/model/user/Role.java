package com.rftdevgroup.transporthub.data.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class specifying a role a given {@link User} has.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    @Override
    public String toString() {
        return "ROLE_" + name.toUpperCase();
    }

    public static Role byName(String name){
        Role role = new Role();
        role.setName(name);
        return role;
    }
}
