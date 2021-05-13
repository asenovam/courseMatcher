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
public class AweBristol implements UniversityScrapper {

    public UniversityData dataScraping(List<String> universityCoursesLink) {
        UniversityData universityData = new UniversityData();
        universityData.setUrls(universityCoursesLink);
        universityData.setName("AWE Bristol");

        try {
            for (String courseLink : universityCoursesLink) {
                Document document = Jsoup.connect(courseLink).get();
                Set<String> courseUrlsPost = new HashSet<>();

                Elements hrefsOnPage = document.select("a[href]");
                for (Element href : hrefsOnPage) {
                    String element = href.absUrl("href");
                    if (element.contains("courses.uwe.ac.uk/")) {
                        courseUrlsPost.add(element);
                    }
                }
                for (String modPage : courseUrlsPost) {
                    CourseData courseData = new CourseData();
                    
                    courseData.setDegree(UniversityDegree.MASTER);
                    Document course = Jsoup.connect(modPage).get();
                    Elements courseTitleUg = course.select("h1");
                    courseData.setTitle(courseTitleUg.text());
                    courseData.setUrl(modPage);
                    courseData.setUniversityName("AWE Bristol");
                    // System.out.println(title.text());
                    Elements modules = course.select("strong");
                    for (Element elem : modules) {
                        String elementAsString = String.valueOf(elem);
                        if (!elementAsString.contains("requirements") && !elementAsString.contains("Selection") && !elementAsString.contains("Personal") && !elementAsString.contains("Applicants ")
                                && !elementAsString.contains("Deferrals") && !elementAsString.contains("Email:") && !elementAsString.contains("Telephone:") && !elementAsString.contains("trainees ")
                                && !elementAsString.contains("English language support") && !elementAsString.contains("applicants") && !elementAsString.contains("?") && !elementAsString.contains("Core modules")
                                && !elementAsString.contains("Option modules") && !elementAsString.contains("from this list") && !elementAsString.contains("attendance") && !elementAsString.contains("Dedicated")
                                && !elementAsString.contains("Pro Bono Unit") && !elementAsString.contains("Student representation") && !elementAsString.contains("UWE-Münster") && !elementAsString.contains("UWE Bristol's")
                                && !elementAsString.contains("Get inspired") && !elementAsString.contains("Course structure") && !elementAsString.contains("Flexible learning of valuable skills") && !elementAsString.contains("Part-time")
                                && !elementAsString.contains("start") && !elementAsString.contains("Study support") && !elementAsString.contains("January") && !elementAsString.contains("Meet leaders of industry") && !elementAsString.contains("Year")
                                && !elementAsString.contains(" March") && !elementAsString.contains("April") && !elementAsString.contains("visa") && !elementAsString.contains("Health") && !elementAsString.contains("Criminal Background") && !elementAsString.contains("international students")
                                && !elementAsString.contains("Full time") && !elementAsString.contains("Deadline") && !elementAsString.contains("year") && !elementAsString.contains("years")
                                && !elementAsString.contains("full-time route") && !elementAsString.contains("Potential exemptions") && !elementAsString.contains("Career-enhancing ") && !elementAsString.contains("doorstep")
                                && !elementAsString.contains("Different awards") && !elementAsString.contains(":") && !elementAsString.contains("Optional modules") && !elementAsString.contains("Compulsory modules") && !elementAsString.contains("Studying in Bristol")
                                && !elementAsString.contains("Strong links with industry") && !elementAsString.contains("CIPD recognition") && !elementAsString.contains("Full-time") && !elementAsString.contains("modules")
                                && !elementAsString.contains("Phase two") && !elementAsString.contains("Any travel involved will be at your own expense.") && !elementAsString.contains("sciences") && !elementAsString.contains("qualifications")
                                && !elementAsString.contains("Other essential criteria") && !elementAsString.contains("Work experience") && !elementAsString.contains("References") && !elementAsString.contains("Interview") && !elementAsString.contains("Professional registration")
                                && !elementAsString.contains("Please apply for the") && !elementAsString.contains("Flexible and accessible modes of study") && !elementAsString.contains("Tailored to enhance your employment opportunities")
                                && !elementAsString.contains("English Language support") && !elementAsString.contains("Please note, this course is not available to students who require a Student Visa.") && !elementAsString.contains("Advance your career")
                                && !elementAsString.contains("Hone in on your career aspirations") && !elementAsString.contains("*") && !elementAsString.contains("English Language support") && !elementAsString.contains(" 2021")
                                && !elementAsString.contains(" placement and your personal therapist") && !elementAsString.contains(" Where it can take you") && !elementAsString.contains("social worker") && !elementAsString.contains(" first level nurse") && !elementAsString.contains(" occupational therapist")
                                && !elementAsString.contains("chartered psychologist") && !elementAsString.contains("If you intend to become a Solicitor") && !elementAsString.contains("Awards and scholarships")
                                && !elementAsString.contains("Where it can take you")) {
                            if (elem.text().length() > 10) {
                                System.out.println(elem.text());
                                ModuleData module = new ModuleData();
                                
                                 String moduleName = elem.text();
                                  if (moduleName != null && moduleName.endsWith("credits)")) {
                                      moduleName = moduleName.substring(0, moduleName.length() - 13);
                                  }
                                  
                                module.setName(moduleName);
                                courseData.getModules().add(module);
                            }
                        }
                    }
                    universityData.getCourses().add(courseData);
                }
            }
        } catch (Exception e) {
            System.err.println("Error during the scraing of the university data " + e.getMessage());
        }

        return universityData;

    }
}
