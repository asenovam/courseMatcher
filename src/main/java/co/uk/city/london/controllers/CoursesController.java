/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.beans.CompareCoursesBean;
import co.uk.city.london.beans.CourseBean;
import co.uk.city.london.beans.CourseSearchBean;
import co.uk.city.london.converters.CourseEntityToBeanConverter;
import co.uk.city.london.daos.CourseDao;
import co.uk.city.london.daos.UserDao;
import co.uk.city.london.entities.CourseEntity;
import co.uk.city.london.entities.UniversityDegree;
import co.uk.city.london.entities.UserEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.uk.city.london.services.ModuleComparator;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mar_9
 */
@Controller
public class CoursesController {
    
    @Autowired
    private CourseDao courseDao;
    
    @Autowired
    private UserDao userDao;
    
   @Autowired
    private   CourseEntityToBeanConverter courseEntityToBeanConverter;
   
   @Autowired
   private ModuleComparator moduleComparator;
   
   
   @PostConstruct
   public void postSetUp() {
      System.out.println("Hello I am after construction of CourseController");
   }
   
   @PreDestroy
   public void postDestroy() {
       System.out.println("I am gonnabe called justbefore bean detroy - when the server stoppsr");
   }
   
   
    @GetMapping("/searchbycourse")
    public String searchByMoules(Model model, @RequestParam(value = "nickname", required = false)  String nickname) {
        System.out.println(" nickname GET " + nickname);
        
        CourseSearchBean search = new CourseSearchBean();
        search.setNickname(nickname);
        search.setUniversityDegree(UniversityDegree.BACHELOR.name());
        model.addAttribute("search", search);
        
        model.addAttribute("courses", new ArrayList<CourseEntity>());
        CompareCoursesBean compareCoursesBean = new CompareCoursesBean();
        compareCoursesBean.setNickname(nickname);
        
        model.addAttribute("selectedCourses" ,  compareCoursesBean);
        
        return "searchbycourse";
    }
    
    @PostMapping("/searchbycourse")
    public String searchbycourse(@ModelAttribute CourseSearchBean search,  BindingResult bindingResult, Model model) {
        return findCourses(search, bindingResult, model, search.getUniversityDegree());
    }

    private String findCourses(CourseSearchBean search,  BindingResult bindingResult, Model model, String degree) {
           System.out.println(" course name " + search.getCourseTitle());
        System.out.println(" nickname POST " + search.getNickname());
        System.out.println(" degree " + degree );
        
        UniversityDegree degreeAsEnum  = UniversityDegree.getByName(degree);
        List<CourseEntity> allCourses = courseDao.findByTitleRegex(".*" + search.getCourseTitle() + ".*");
        List<CourseEntity> matchedCourses = allCourses.stream().filter(c -> degreeAsEnum.equals(c.getDegree())).collect(Collectors.toList());
        if (matchedCourses == null || matchedCourses.size() == 0) {
               allCourses = courseDao.findByTitleRegex("/" + search.getCourseTitle() + "/");
                matchedCourses = allCourses.stream().filter(c -> degreeAsEnum.equals(c.getDegree())).collect(Collectors.toList());

               System.out.println("SECOND OPTION WAS REUIQRED");
        }
      
        System.out.println (" matched courses " + matchedCourses);
        if (matchedCourses != null && matchedCourses.size() > 0) {
             System.out.println (" matched courses size  " + matchedCourses.size());
             System.out.println (" course e  " + matchedCourses.get(0).getTitle() + matchedCourses.get(0).getDegree()             );
             
        }
        if (matchedCourses == null || matchedCourses.size() == 0) {
            search.setErrorMesssage("no courses matched with title: " + search.getCourseTitle());
            
            model.addAttribute("search", search);
           
            return "searchbycourse";
        }
        
        if (matchedCourses != null) {
            for (CourseEntity course : matchedCourses) {
                System.out.println(course.getTitle() + ",  number of modules " + course.getTitle());
            }
        }
        
        model.addAttribute("search", search);
        model.addAttribute("courses", matchedCourses);
        CompareCoursesBean compareCoursesBean = new CompareCoursesBean();
        compareCoursesBean.setNickname(search.getNickname());
        model.addAttribute("selectedCourses" , compareCoursesBean);
        
     //   attr.addAttribute("courses", matchedCourses);
      //  attr.addAttribute("search", search);
            
         return "searchbycourse";
    
    }
    
    @PostMapping("/comparecourses")
    public String comparecourses(@ModelAttribute CompareCoursesBean compareCourses,  BindingResult bindingResult, RedirectAttributes attr,  Model model) {
         System.out.println("selected coures in POST " +  compareCourses.getSelectedCourses());
         System.out.println(" compatre nickname  POST  " + compareCourses.getNickname());
         
         attr.addAttribute("selectedCourses", compareCourses.getSelectedCourses());
         attr.addAttribute("nickname", compareCourses.getNickname());
        
        return  "redirect:comparecourses";
    }
    
   
    @GetMapping("/comparecourses")
    public String getComparecourses( Model model,  String  selectedCourses, @RequestParam(value = "nickname", required = false)  String nickname) {
        System.out.println("selected coures in GET " +  selectedCourses );
        System.out.println(" compare GET nickname  " +  nickname);
        
        boolean areModeulesPopulated = false;
        if (selectedCourses != null) { 
            String[] ids = selectedCourses.trim().split(",");
            if (ids != null && ids.length == 2) {
                populateModules(ids, model);
                areModeulesPopulated = true;
            }
        }
        
        CourseSearchBean courseSearchBean = new CourseSearchBean();
        courseSearchBean.setNickname(nickname);
        if (!areModeulesPopulated) {
            courseSearchBean.setErrorMesssage("Please select 2 courses");
        }
        model.addAttribute("compareCoursesBean" , courseSearchBean);
        
        return  "comparecourses";
    }
   
    @PostMapping("/addcourse")
    public String addCourse( @ModelAttribute CompareCoursesBean compareCourses,  BindingResult bindingResult, RedirectAttributes attr,  Model model) {
          System.out.println("add course post nickname " + compareCourses.getNickname());
          String nickname = compareCourses.getNickname();
          String courseId = compareCourses.getMyCourse();
          if (nickname != null && !nickname.isEmpty() && courseId != null && !courseId.isEmpty()) {
                CourseEntity myNewCourse = courseDao.findById(courseId).orElse(null);
                UserEntity loggedUser = userDao.findByNickname(nickname);
                if (myNewCourse != null && loggedUser != null) {
                       if (!loggedUser.getCourseUrls().contains(myNewCourse.getUrl())) {
                              loggedUser.getCourseUrls().add(myNewCourse.getUrl());
                               userDao.save(loggedUser);                           
                       }
                }
          }
        
         attr.addAttribute("selectedCourses", compareCourses.getSelectedCourses());
         attr.addAttribute("nickname", nickname);
         
         return  "redirect:comparecourses";
    }
    
    private void populateModules( String[] ids , Model model) {
          System.out.println("ids " + ids);

          if (ids != null &&  ids.length > 1) {
              CourseEntity course1 = courseDao.findById(ids[0]).orElse(null);
              CourseEntity course2 = courseDao.findById(ids[1]).orElse(null);

              CourseBean courseBean1 = new CourseBean();
              courseEntityToBeanConverter.convert(course1, courseBean1);

              CourseBean courseBean2 = new CourseBean();
              courseEntityToBeanConverter.convert(course2, courseBean2);

              moduleComparator.compareModules(courseBean1, courseBean2);

              model.addAttribute("course1", courseBean1);
              model.addAttribute("course2", courseBean2);
          } else {
               model.addAttribute("course1", null);
               model.addAttribute("course2", null);
          }
    }
   
    @GetMapping("/search")
    public String greeting(Model model) {
        model.addAttribute("search", new CourseSearchBean());
        model.addAttribute("courses", new ArrayList<CourseEntity>());
         
        return "search";
    }

    @PostMapping("/search")
    public String greetingSubmit(@ModelAttribute CourseSearchBean search, Model model, RedirectAttributes attr) {

        List<CourseEntity> matchedCourses = courseDao.findByTitleRegex(".*" + search.getCourseTitle() + ".*");
        if (matchedCourses == null || matchedCourses.size() == 0) {
           matchedCourses = courseDao.findByTitleRegex("/" + search.getCourseTitle() + "/");
           System.out.println("SECOND OPTION WAS REUIQRED");
       }
      
        if (matchedCourses == null || matchedCourses.size() == 0) {
            search.setErrorMesssage("no courses matched with title: " + search.getCourseTitle());
            return "search";
        } 
        
        if (matchedCourses != null) 
        {
            for (CourseEntity course : matchedCourses) {
                System.out.println(course.getTitle() + ",  number of modules " + course.getTitle());
            }
        }
        
        if (search.getErrorMesssage() == null) {
            search.setErrorMesssage("");
        }
        
        model.addAttribute("search", search);
        model.addAttribute("courses", matchedCourses);
        
        attr.addAttribute("courses", matchedCourses);

        return "search";
    }

}
