/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.converters;

import co.uk.city.london.beans.RegisterBean;
import co.uk.city.london.entities.UserEntity;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class RegisterConverter {
    
    public void convert(RegisterBean registerBean, UserEntity user) {
        user.setEmail(registerBean.getEmail());
        user.setNickname(registerBean.getNickname());
        user.setPassword(registerBean.getPassword());
        user.setFirstName(registerBean.getFirstName());
        user.setLastName(registerBean.getLastName());
      
        long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        user.setUid(id);
    }
}
