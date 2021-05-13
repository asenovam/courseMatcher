/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services;

import co.uk.city.london.builder.EntityCourseBuilder;
import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.UniversityDegree;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import co.uk.city.london.beans.CourseBean;
import co.uk.city.london.builder.CourseBeanBuilder;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author mar_9
 */
@RunWith(MockitoJUnitRunner.class)
public class ModuleComparatorTest {
    
    @InjectMocks    
    private ModuleComparator moduleComparator;
    
    @Test
    public void whenModuleNamesAreEquals_theyShouldHaveSameColor() {
        
        CourseBean course1 = new CourseBeanBuilder().setTitle( "software enginner in city london univerity").addModule("Maths", "8594u").build();
        CourseBean course2 = new CourseBeanBuilder().setTitle( "software in Manchester").addModule("Maths", "jfidfd8").build();
        
        moduleComparator.compareModules(course1, course2);
        
        Assert.assertEquals((course1.getModules().get(0).getColor()), course2.getModules().get(0).getColor());
    }
    
     @Test
    public void whenModuleNamesAreNotEquals_theyShouldRemainWithsameColor() {
        
        CourseBean course1 = new CourseBeanBuilder().setTitle( "software enginner in city london univerity").addModule("Internet technologies", "jjhdjfdhf").build();
        CourseBean course2 = new CourseBeanBuilder().setTitle( "software in Manchester").addModule("Maths", "dhewuywe8").build();
        
        moduleComparator.compareModules(course1, course2);
        
        Assert.assertNotEquals((course1.getModules().get(0).getColor()), course2.getModules().get(0).getColor());
    }
}
