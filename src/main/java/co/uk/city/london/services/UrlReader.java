/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.services;

import co.uk.city.london.services.uniExtractors.data.UniConfigData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mar_9
 */
@Component
public class UrlReader {
    
    public List<UniConfigData> readUniConfigs() {
        
        List<UniConfigData> uniConfigs = null;
        try {
              uniConfigs = loadUniConfigs();
        } catch (Exception e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
        }
//        List<UniConfigData> uniConfigs = new ArrayList<>();
//        Scanner myReader = null;
//        try {
//            
//           File myUniConfigs = new File("/configs/universitieslist.txt");
//            
//            myReader = new Scanner(myUniConfigs);
//            while (myReader.hasNextLine()) {
//                UniConfigData uni = new UniConfigData();
//                String uniLine = myReader.nextLine();
//                int indexOfSeparator = uniLine.indexOf("|");
//                String uniName = uniLine.substring(indexOfSeparator);
//                uni.setUniName(uniName);
//                String uniCoursesLine = uniLine.substring(indexOfSeparator + 1, uniLine.length() - 1);
//                for (String coursesUrl : uniCoursesLine.split(",")) {
//                    uni.getCourseUrls().add(coursesUrl);
//                }
//            }
//            myReader.close();
//        } catch (Exception e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        } finally {
//            if (myReader != null) {
//                 myReader.close();                
//            }
//        }
//        
        return uniConfigs;
    }
    
    private List<UniConfigData>  loadUniConfigs() throws Exception {
         List<UniConfigData> uniConfigs = new ArrayList<>();
         InputStream is = getClass().getClassLoader().getResourceAsStream("configs/universitieslist.txt");
          
        try (InputStreamReader streamReader =
                    new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {

            String uniLine;
            while ((uniLine = reader.readLine()) != null) {
               UniConfigData uni = new UniConfigData();
                int indexOfSeparator = uniLine.indexOf("|");
                String uniName = uniLine.substring(0, indexOfSeparator);
                uni.setUniName(uniName);
                String uniCoursesLine = uniLine.substring(indexOfSeparator + 1, uniLine.length());
                for (String coursesUrl : uniCoursesLine.split(",")) {
                    uni.getCourseUrls().add(coursesUrl);
                }
                
                uniConfigs.add(uni);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniConfigs;
    }

}
