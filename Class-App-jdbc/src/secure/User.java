/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secure;

import java.io.Serializable;

/**
 *
 * @author elau
 */
public class User implements Serializable {
    
    private final String userId;
    private final String name;
    private final String phone;
    private final String address;
    private final String email;
    private final String password;
    private final String userGroup;
    private final boolean active; 

    public User(String userId, String name, String phone, String address, 
            String email, String password, String userGroup, boolean active) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userGroup = userGroup;
        this.active = active;       // whether the user is currently active
    }

    public String getUserid() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public String getUserGroup() {
        return userGroup;
    }

    public boolean isActive() {
        return active;
    }

}
