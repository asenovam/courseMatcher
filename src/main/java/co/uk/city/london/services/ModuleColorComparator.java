/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services;

import co.uk.city.london.beans.ModuleBean;
import co.uk.city.london.converters.CourseEntityToBeanConverter;
import co.uk.city.london.entities.ModuleEntity;
import java.util.Comparator;

/**
 *
 * @author mar_9
 */
public class ModuleColorComparator  implements Comparator<ModuleBean>{

    @Override
    public int compare(ModuleBean m1, ModuleBean m2) {
            if(CourseEntityToBeanConverter.DEFAULT_COLOR.equals(m1.getColor())) {
                    return 1;
            }
            if(CourseEntityToBeanConverter.DEFAULT_COLOR.equals(m2.getColor())) {
                    return -1;
            }
            return m1.getColor().compareTo(m2.getColor());
    }

     
}
