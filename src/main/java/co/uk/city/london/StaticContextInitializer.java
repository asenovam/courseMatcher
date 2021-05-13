/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class StaticContextInitializer {
    
    @Autowired
    private MappingMongoConverter mappingMongoConverter;
    
    @PostConstruct
    public void init() {
     mappingMongoConverter.setMapKeyDotReplacement("_");
    }
    
}
