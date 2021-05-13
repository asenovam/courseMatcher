
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package co.uk.city.london;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import co.uk.city.london.beans.RegisterBean;
import co.uk.city.london.converters.RegisterConverter;
import co.uk.city.london.entities.UserEntity;

public class RegisterConverterTest {
    RegisterConverter register = new RegisterConverter();

    @Test
    public void isProfileSuccessfullyCreated() {
        UserEntity   testUser = new UserEntity();
        RegisterBean regBean  = new RegisterBean();

        regBean.setEmail("marchelaasenova@icloud.com");
        regBean.setNickname("Chela");
        regBean.setPassword("789456");
        regBean.setFirstName("Marchela");
        regBean.setLastName("Asenova");
        register.convert(regBean, testUser);
        assertEquals("marchelaasenova@icloud.com", testUser.getEmail());
        assertEquals("Chela", testUser.getNickname());
        assertEquals("789456", testUser.getPassword());
        assertEquals("Marchela", testUser.getFirstName());
        assertEquals("Asenova", testUser.getLastName());
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
