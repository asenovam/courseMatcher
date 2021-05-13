/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services.uniExtractors.universityScrappers;

import co.uk.city.london.entities.UniversityDegree;
import co.uk.city.london.services.UniversityScrapper;
import co.uk.city.london.services.uniExtractors.data.CourseData;
import co.uk.city.london.services.uniExtractors.data.ModuleData;
import co.uk.city.london.services.uniExtractors.data.UniversityData;
import java.util.List;


import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author mar_9
 */
public class YorkUniversityScrapper implements UniversityScrapper {
    
     public UniversityData dataScraping(final List<String> universityCoursesLink) {
       
         
         UniversityData universityData = new UniversityData();
         universityData.setUrls(universityCoursesLink);
         universityData.setName("University Of York");
         
         try {
              final Set<String> allCourses = new HashSet<>();
              for (String courseLink : universityCoursesLink) {
                    System.out.println("\n\n course link: " + courseLink);
                    
                    Document coursesDocument = Jsoup.connect(courseLink).get();
                    Elements coursesLinks = coursesDocument.select("a[href]");
                    for (Element link : coursesLinks) {
                         String currentLink = link.absUrl("href");
                         if ((currentLink.contains("www.york.ac.uk/study/undergraduate/courses/") && currentLink.length() > 59 && !currentLink.contains("#"))
                             || (currentLink.contains("www.york.ac.uk/study/postgraduate-taught/courses/") && currentLink.length() > 9 && !currentLink.contains("#"))) {
                            allCourses.add(currentLink);
                        }
                    }
              }
              
              for (String courseUrl : allCourses) {
                  CourseData courseData = new CourseData();
                  
                  Document modulePage = Jsoup.connect(courseUrl).get();
                  Elements title = modulePage.getElementsByTag("h1");
                   courseData.setTitle(title.text());
                   courseData.setUrl(courseUrl);
                   courseData.setUniversityName("University Of York");
                   if (courseUrl.contains("www.york.ac.uk/study/undergraduate/courses")) {
                       courseData.setDegree(UniversityDegree.BACHELOR);
                   } else {
                       courseData.setDegree(UniversityDegree.MASTER);
                   }
                  
                  Elements moduleSections = modulePage.getElementsByTag("ul");
                  for (Element modulesSection : moduleSections) {
                       String subModuleFilter = String.valueOf(modulesSection);
                       if (subModuleFilter.contains("<li><strong><a href=\"https://www.york.ac.uk/students/studying/manage/programmes/module-catalogue/module")) {
                             Elements moduleSubLiSections = modulesSection.getElementsByTag("li");
                             for (Element subLi : moduleSubLiSections) {
                                 String mods=String.valueOf(subLi);
                                  ModuleData moduleData = new ModuleData();
                                  System.err.println("module name: " + subLi.text());
                                  String moduleName = subLi.text();
                                  if (moduleName != null && moduleName.endsWith("credits)")) {
                                      moduleName = moduleName.substring(0, moduleName.length() - 13);
                                  }
                                  moduleData.setName(moduleName);
                                  courseData.getModules().add(moduleData);                             
                             }
                       }
                  }
                  
                  universityData.getCourses().add(courseData);
             }
          
         } catch (IOException ioe) {
             System.err.println("The uni data can not be read at the moment. Maybe site is down " + universityCoursesLink);
         }
         
         return universityData;
     }
}
