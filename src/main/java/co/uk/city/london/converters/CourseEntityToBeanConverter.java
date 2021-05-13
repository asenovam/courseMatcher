/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.converters;

import co.uk.city.london.beans.CourseBean;
import co.uk.city.london.beans.ModuleBean;
import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.ModuleEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class CourseEntityToBeanConverter {
    
    public static final String DEFAULT_COLOR = "#778899";
    
    public void convert(CourseEntity entity, CourseBean bean) {
        bean.setId(entity.getId());
        bean.setTitle(entity.getTitle());
        bean.setUrl(entity.getUrl());
        bean.setUniversityDegree(entity.getDegree() != null ? entity.getDegree().name() : null);
        bean.setUniversityName(entity.getUniversityName());
        
        List<ModuleBean> modules = new ArrayList<>();
        for (ModuleEntity moduleEntity : entity.getModules()) {
            ModuleBean moduleBean = new ModuleBean();
            moduleBean.setName(moduleEntity.getName());
            moduleBean.setColor(DEFAULT_COLOR);
            modules.add(moduleBean);
        }
        
        bean.setModules(modules);
    }
}
