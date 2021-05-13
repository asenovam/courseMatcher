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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author mar_9
 */
public class LancasterUniversity implements UniversityScrapper {

    @Override
    public UniversityData dataScraping(List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("Kingston University");
        try {
            Set<CourseUrlContainer> allCoursesLinks = new HashSet<>();
            Document undergrads = Jsoup.connect(universityCoursesLink.get(0)).get();
            Elements links = undergrads.select("a[href]");
            String trim = "";
            for (Element s : links) {
                String ls = String.valueOf(s);
                if (ls.contains("/study/undergraduate/courses/")) {
                    trim = ls.substring(9, ls.length());

             
                }
                int index = 0;
                for (int i = 0; i < trim.length(); i++) {
                    if (trim.charAt(i) == '"') {
                        index = i;
                    }
                }
                String trimUrl = "https://www.lancaster.ac.uk".concat(trim.substring(0, index) + "#structure");
                if (!trimUrl.contains("svg") && trimUrl.length() > 49) {
                    allCoursesLinks.add(new CourseUrlContainer(UniversityDegree.BACHELOR, trimUrl));
                }

            }
            Document postgrads = Jsoup.connect(universityCoursesLink.get(1)).get();
            Elements urls = postgrads.select("a[href]");
            String cut = "";
            for (Element s : urls) {
                String ls = String.valueOf(s);
                // if (ls.contains("/study/postgraduate/courses/")) {
                cut = ls.substring(9, ls.length());

                //     System.out.println(cut);
                //  }
                int index = 0;
                for (int i = 0; i < cut.length(); i++) {
                    if (cut.charAt(i) == '"') {
                        index = i;
                    }
                }

                String trimUrl = "https://www.lancaster.ac.uk".concat(cut.substring(0, index));
                if (!trimUrl.contains("svg") && trimUrl.length() > 60 && !trimUrl.contains("https") && 
                        !trimUrl.contains("why") && !trimUrl.contains("fees") && !trimUrl.contains("research")
                        && !trimUrl.contains("entry") && !trimUrl.contains("button") && !trimUrl.contains
        (".com") && !trimUrl.contains("global") && !trimUrl.contains("students")
                        && !trimUrl.contains("planning")) {
                                       allCoursesLinks.add(new CourseUrlContainer(UniversityDegree.MASTER, trimUrl));
             
                }
            }

            for (CourseUrlContainer s : allCoursesLinks) {
              
                CourseData courseData = new CourseData();
                Document coursePage = Jsoup.connect(s.url).get();
                courseData.setDegree(s.degree);

                Elements courseTitle = coursePage.select("h1");
                System.out.println("TITLE" + " " + courseTitle.text());
                courseData.setTitle(courseTitle.text());
                courseData.setUrl(s.url);
                courseData.setUniversityName("Lancaster University");

                Elements urlElements = coursePage.select("a[href]");
                for (Element e : urlElements) {
                    String element = String.valueOf(e);
                    if (element.contains("<a href=\"#\" class=\"accordion-title\" role=\"tab\" "
                            + "aria-expanded=\"false\" aria-selected=")) {
                        System.out.println(e.text());
                        ModuleData module = new ModuleData();
                        module.setName(e.text());
                        courseData.getModules().add(module);
                    }
                }
                universityData.getCourses().add(courseData);
            }
        } catch (Exception e) {
            System.err.println("Error during the scraing of the university data " + e.getMessage());
        }

        return universityData;
    }
    
    public static class CourseUrlContainer {
        public  UniversityDegree degree;
        public String url;

        public CourseUrlContainer(UniversityDegree degree, String url) {
            this.degree = degree;
            this.url = url;
        }
    }
}
