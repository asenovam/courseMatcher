/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.beans.CompareCoursesBean;
import co.uk.city.london.beans.CourseSearchBean;
import co.uk.city.london.builder.EntityCourseBuilder;
import co.uk.city.london.converters.CourseEntityToBeanConverter;
import co.uk.city.london.daos.CourseDao;
import co.uk.city.london.daos.UserDao;
import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.UniversityDegree;
import co.uk.city.london.services.ModuleComparator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author mar_9
 */

@RunWith(MockitoJUnitRunner.class)
public class CoursesControllerTest {
    
    @InjectMocks
    private CoursesController coursesController;
    
   @Mock
    private CourseDao courseDao;
    
    @Mock
    private UserDao userDao;
    
    @Mock
    private   CourseEntityToBeanConverter courseEntityToBeanConverter;
   
    @Mock
    private ModuleComparator moduleComparator;
    
    @Mock
    private Model model;
    
    @Mock
    private  BindingResult bindingResult;
    
    @Test
    public void whenSearchByModuesIsBeingCalled_thenBeansInitializationIsExecuted() {
        String nickname = "test@test.com";
        coursesController.searchByMoules(model, nickname);
        
        // verify the CourseSearchBean bean priperties are initialised and added to the model 
        verify(model, times(1)).addAttribute(anyString(), any(CourseSearchBean.class));
        verify(model, times(1)).addAttribute(anyString(), any(ArrayList.class));
    }
    
    @Test
    public void whenLoadCoursesIsCalled_thenCoursesAreQuiredAndInitialized() {
       CourseSearchBean courseSearchBean = new CourseSearchBean();
       courseSearchBean.setCourseTitle(  "software engineer");
       courseSearchBean.setNickname( "test@test.com");
       courseSearchBean.setUniversityDegree("BACHELOR");
       
        CourseEntity course1 = new EntityCourseBuilder().setTitle( "software engineer in technologies")
                .addModule("Maths").addModule("Project management").setDegree(UniversityDegree.BACHELOR).build();
        CourseEntity course2 = new EntityCourseBuilder().setTitle( "software engineer in master technologies")
                .addModule("Maths").addModule("Project management").setDegree(UniversityDegree.MASTER).build();
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(course2);
        courses.add(course1);
       when(courseDao.findByTitleRegex(anyString())).thenReturn(courses);
       
       
       coursesController.searchbycourse(courseSearchBean, bindingResult, model);
        
        verify(model, times(1)).addAttribute(anyString(), any(CompareCoursesBean.class));
        verify(model, times(1)).addAttribute(anyString(), any(ArrayList.class));
    }
     
    @Test
    public void whenLoadCoursesIsCalled_andNoCourtsesFound_thenCoursesAreNotSetInModel() {
       CourseSearchBean courseSearchBean = new CourseSearchBean();
       courseSearchBean.setCourseTitle(  "software engineer");
       courseSearchBean.setNickname( "bob@test.com");
       courseSearchBean.setUniversityDegree("BACHELOR");
       
        CourseEntity course2 = new EntityCourseBuilder().setTitle( "software engineer in master technologies")
                .addModule("Maths").addModule("Project management").setDegree(UniversityDegree.MASTER).build();
        List<CourseEntity> courses = new ArrayList<>();
        courses.add(course2);
       when(courseDao.findByTitleRegex(anyString())).thenReturn(courses);
       
       
        coursesController.searchbycourse(courseSearchBean, bindingResult, model);
        
        verify(model, times(0)).addAttribute(anyString(), any(ArrayList.class));
    }
    
   
}
