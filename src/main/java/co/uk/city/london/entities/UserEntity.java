/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.entities;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author mar_9
 */
public class UserEntity {

    public static Long idGenerator = Long.valueOf(0);
    
    @Id
    public String id;
    
    private long uid;

    @Indexed(unique = true)
    private String nickname;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;
    
    private List<String> courseUrls = new ArrayList<>();
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public static Long getIdGenerator() {
        return idGenerator;
    }

    public static void setIdGenerator(Long idGenerator) {
        UserEntity.idGenerator = idGenerator;
    }

    public List<String> getCourseUrls() {
        return courseUrls;
    }

    public void setCourseUrls(List<String> courseUrls) {
        this.courseUrls = courseUrls;
    }
    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nickname=" + nickname + ", password=" + password + '}';
    }

}
