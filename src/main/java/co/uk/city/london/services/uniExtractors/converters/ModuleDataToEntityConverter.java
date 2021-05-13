/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.converters;

import co.uk.city.london.entities.ModuleEntity;
import co.uk.city.london.services.uniExtractors.data.ModuleData;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class ModuleDataToEntityConverter {
    
    public void convert(ModuleData moduleData, ModuleEntity moduleEntity) {
        moduleEntity.setDescription(moduleData.getDescription());
        moduleEntity.setName(moduleData.getName());
    }
}
