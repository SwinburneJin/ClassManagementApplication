/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import session.UserManagementRemote;
import entity.UserDTO;
import entity.ClassDTO;
import javax.mail.Authenticator;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named(value = "myUserManagedBean")
@ConversationScoped
public class MyUserManagedBean implements Serializable {

    @Inject
    private Conversation conversation;
    @EJB
    private UserManagementRemote userManagement;

    private String userid;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String userGroup;
    private Boolean active;
    private ArrayList<ClassDTO> classes;

    /**
     * Creates a new instance of MyUserManagedBean
     */
    public MyUserManagedBean() {
        userid = null;
        name = null;
        phone = null;
        address = null;
        email = null;
        password = null;
        newPassword = null;
        confirmPassword = null;
        userGroup = null;
        active = false;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Boolean isActive() {
        return active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }

    public ArrayList<ClassDTO> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<ClassDTO> classes) {
        this.classes = classes;
    }

    public String addUser() {
        // check userid is null
        if (isNull(userid)) {
            return "debug";
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHashString = bytesToHex(passwordHash);
            // all information seems to be valid
            // try add the user
            UserDTO userDTO = new UserDTO(userid, name, phone,
                    address, email, passwordHashString, userGroup, active);
            boolean result = userManagement.addUser(userDTO);
            if (result) {
                this.sendEmail("create");
                return "success";
            } else {
                return "failure";
            }
        } catch (NoSuchAlgorithmException e) {
            return "failure";
        }

    }

    public String setUserDetailsForChange() {
        // check userid is null
        if (isNull(userid) || conversation == null) {
            return "debug";
        }

        if (!userManagement.hasUser(userid)) {
            return "failure";
        }

        // note the startConversation of the conversation
        startConversation();

        // get user details
        return setUserDetails();
    }

    public String changeUser() {
        // check userid is null
        if (isNull(userid)) {
            return "debug";
        }

        UserDTO userDTO = new UserDTO(userid, name, phone,
                address, email, password, userGroup, active);
        boolean result = userManagement.updateUserDetails(userDTO);

        // note the endConversation of the conversation
        endConversation();

        if (result) {
            this.sendEmail("changed");
            return "success";
        } else {
            return "failure";
        }
    }

    public void validateNewPassword(FacesContext context,
            UIComponent componentToValidate, Object value)
            throws ValidatorException {

        // get new password
        String oldPwd = (String) value;

        // get old password
        UIInput newPwdComponent = (UIInput) componentToValidate.getAttributes().get("newpwd");
        String newPwd = (String) newPwdComponent.getSubmittedValue();

        if (oldPwd.equals(newPwd)) {
            FacesMessage message = new FacesMessage(
                    "Old Password and New Password are the same! They must be different.");
            throw new ValidatorException(message);
        }

    }

    public void validatePasswordPair(FacesContext context,
            UIComponent componentToValidate,
            Object pwdValue) throws ValidatorException {

        // get password
        String pwd = (String) pwdValue;

        // get confirm password
        UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
        String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();

        System.out.println("password : " + pwd);
        System.out.println("confirm password : " + cnfPwd);

        if (!pwd.equals(cnfPwd)) {
            FacesMessage message = new FacesMessage(
                    "Password and Confirm Password are not the same! They must be the same.");
            throw new ValidatorException(message);
        }
    }

    public void validateNewPasswordPair(FacesContext context,
            UIComponent componentToValidate,
            Object newValue) throws ValidatorException {

        // get new password
        String newPwd = (String) newValue;

        // get confirm password
        UIInput cnfPwdComponent = (UIInput) componentToValidate.getAttributes().get("cnfpwd");
        String cnfPwd = (String) cnfPwdComponent.getSubmittedValue();

        System.out.println("new password : " + newPwd);
        System.out.println("confirm password : " + cnfPwd);

        if (!newPwd.equals(cnfPwd)) {
            FacesMessage message = new FacesMessage(
                    "New Password and Confirm New Password are not the same! They must be the same.");
            throw new ValidatorException(message);
        }
    }

    public String changeUserPassword() {
        String passwordHashString = null;
        String newPasswordHashString = null;

        // check userid is null
        if (isNull(userid)) {
            return "debug";
        }

        UserDTO u = userManagement.getUserDetails(userid);

        System.out.println("TEST 1");

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (password != null) {
                byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                passwordHashString = bytesToHex(passwordHash);
            }
            if (newPassword != null) {
                byte[] newPasswordHash = digest.digest(newPassword.getBytes(StandardCharsets.UTF_8));
                newPasswordHashString = bytesToHex(newPasswordHash);
            }

            System.out.println("TEST 2");

            if ((password != null && !u.getPassword().equals(passwordHashString))) {
                // Current password wrong
                return "failure";
            }

            // newPassword and confirmPassword are the same - checked by the validator during input to JSF form
            boolean result = userManagement.updateUserPassword(userid, newPasswordHashString);

            System.out.println("result = " + result);

            if (result) {
                this.sendEmail("changed");
                return "success";
            } else {
                return "failure";
            }
        } catch (NoSuchAlgorithmException e) {
            return "failure";
        }
    }

    public String deleteUser() {
        // check userid is null
        if (isNull(userid)) {
            return "debug";
        }

        boolean result = userManagement.deleteUser(userid);

        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    public String displayUser() {
        // check userid is null
        if (isNull(userid) || conversation == null) {
            return "debug";
        }

        return setUserDetails();
    }

    private boolean isNull(String s) {
        return (s == null);
    }

    private String setUserDetails() {

        String passwordHashString = null;

        if (isNull(userid) || conversation == null) {
            return "debug";
        }

        try {
            UserDTO e = userManagement.getUserDetails(userid);

            if (password != null) {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                passwordHashString = bytesToHex(passwordHash);
            }

            if (e == null || (password != null && !e.getPassword().equals(passwordHashString))) {
                // no such user
                return "failure";
            } else {
                // found - set details for display
                this.userid = e.getUserid();
                this.name = e.getName();
                this.phone = e.getPhone();
                this.address = e.getAddress();
                this.email = e.getEmail();
                this.password = e.getPassword();
                this.active = e.isActive();
                this.classes = userManagement.getClasses(userid);
                return "success";
            }
        } catch (NoSuchAlgorithmException e) {
            return "failure";
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private boolean validAddUserInfo() {
        return (userid != null && name != null && phone != null && address != null
                && email != null && password != null && userGroup != null);
    }

    private void sendEmail(String type) {
        Properties props = new Properties();
        props.put("mail.smtp.user", "101458985@student.swin.edu.au");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        String msgText = "";

        if (type.equals("create")) {
            msgText = "Hey there, welcome to the Class Management Application! You can now log in using your email address.";
        } else if (type.equals("changed")) {
            msgText = "Hey there, your CMA user details have been changed. In case this is not done by you, please contact 101458985@student.swin.edu.au immediately!";
        } else if (type.equals("delete")) {
            msgText = "Hey there, your CMA user account has been deactivated. In case this is not done by you, please contact 101458985@student.swin.edu.au immediately!";
        }

        try {
            Authenticator auth = new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("101458985@student.swin.edu.au", "jhlim5kesiA@");
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(msgText);
            msg.setSubject("Message from CMA");
            msg.setFrom(new InternetAddress("101458985@student.swin.edu.au"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            Transport.send(msg);
        } catch (MessagingException mex) {
        }
    }

}
