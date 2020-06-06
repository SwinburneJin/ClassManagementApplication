package entity;

import java.io.Serializable;

public class UserDTO implements Serializable {
    
    String userid;
    String name;
    String phone;
    String address;
    String email;
    String password;
    String userGroup;
    Boolean active;
    
    public UserDTO(String userid, String name, String phone, String address,
            String email, String password, String userGroup, Boolean active) {
        this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userGroup = userGroup;
        this.active = active;
    }

    public String getUserid() {
        return userid;
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

    public Boolean isActive() {
        return active;
    }
    
}
