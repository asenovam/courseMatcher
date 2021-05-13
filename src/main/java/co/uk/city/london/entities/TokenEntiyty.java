/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.entities;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author mar_9
 */
public class TokenEntiyty {
    
    @Indexed(unique=true)
    private String token;
    
    private boolean isUsed;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    
    
    
}
