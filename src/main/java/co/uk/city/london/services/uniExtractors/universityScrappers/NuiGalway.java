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
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author mar_9
 */
public class NuiGalway implements UniversityScrapper {

    @Override
    public UniversityData dataScraping(List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("NUI Galway");
        try {

            Document undergrad = Jsoup.connect(universityCoursesLink.get(0)).get();
            Document postgrad = Jsoup.connect(universityCoursesLink.get(1)).get();
            Set<String> moduleLinks = new HashSet<>();
            
            Set<String> undergradModulePages = new HashSet<>();
            Set<String> postgradModulePages = new HashSet<>();
            
            Elements undrLinks = undergrad.select("a[href]");
            for (Element url : undrLinks) {
                String urls = url.absUrl("href");
                if (urls.contains("http://www.nuigalway.ie/courses/undergraduate-courses/") && urls.contains(".html")) {
                    moduleLinks.add(urls);
                    //   System.out.println(urls);
                }
            }
            Elements postLinks = postgrad.select("a[href]");
            for (Element url : postLinks) {
                String urls = url.absUrl("href");
                if (urls.contains("http://www.nuigalway.ie/courses/taught-postgraduate-courses/../taught-postgraduate-courses") && urls.contains(".html")) {
                    moduleLinks.add(urls);
                    //System.out.println(urls);
                }
            }
            for (String link : moduleLinks) {
                Document module = Jsoup.connect(link).get();
                Elements courseOutline = module.select("a[href]");
                
                if (link.contains("http://www.nuigalway.ie/courses/undergraduate-courses")) {
                     for (Element url : courseOutline) {
                        String modLink = url.absUrl("href");
                        if (modLink.contains("outline")) {
                            undergradModulePages.add(modLink);
                        }
                    }
                } else {
                    for (Element url : courseOutline) {
                        String modLink = url.absUrl("href");
                        if (modLink.contains("outline")) {
                            postgradModulePages.add(modLink);
                        }
                    }
                }
                
            }

            for (String moduleContent : undergradModulePages) {
                CourseData courseData = extractCourse(moduleContent);
                if (courseData != null) {
                    courseData.setDegree(UniversityDegree.BACHELOR);
                    universityData.getCourses().add(courseData);
                }
            }
            
            for (String moduleContent : postgradModulePages) {
                CourseData courseData = extractCourse(moduleContent);
                if (courseData != null) {
                    courseData.setDegree(UniversityDegree.MASTER);
                    universityData.getCourses().add(courseData);
                }
            }
        } catch (IOException ioe) {
            System.err.println("The uni data can not be read at the moment. Maybe site is down " + universityCoursesLink);
        }
        return universityData;

    }
    
    
    private CourseData extractCourse(String moduleContent) {
          CourseData courseData = new CourseData();

          try {
                Document extractData = Jsoup.connect(moduleContent).get();
                Elements courseName = extractData.select("h1");
                courseData.setTitle(courseName.text());
                courseData.setUrl(moduleContent);
                courseData.setUniversityName("NUI Galway");
                int trimIndex = 0;
                String element = "";
                Elements mod = extractData.select("h4");
                for (Element e : mod) {
                    String module = String.valueOf(e);
                    if (module.contains("Required") || module.contains("Optional")) {
                        if (e.text() != null) {

                            String trimModName = String.valueOf(e.text());
                            for (int i = 0; i < e.text().length(); i++) {
                                if (e.text().charAt(i) == ':') {
                                    trimIndex = e.text().indexOf(':');
                                }
                                element = trimModName.substring(trimIndex + 2, trimModName.length());
                                System.out.println(element);
                                ModuleData moduleData = new ModuleData();
                                System.err.println("module name: " + element);
                                moduleData.setName(element);
                                courseData.getModules().add(moduleData);

                            }

                        }
                    }
                }
                
                return courseData;
          } catch (IOException io) {
              System.err.println("Erroring  extracing course " + io);
          }
          
          return null;
    }
}
