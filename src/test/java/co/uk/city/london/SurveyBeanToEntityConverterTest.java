/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london;

import co.uk.city.london.beans.SurveyBean;
import co.uk.city.london.converters.SurveyBeanToEntityConverter;
import co.uk.city.london.entities.SurveyEntity;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author mar_9
 */
public class SurveyBeanToEntityConverterTest {

    private SurveyBeanToEntityConverter converter = new SurveyBeanToEntityConverter();

    @Test
    public void surveyConvertedSucesfully() {
        SurveyBean survey = new SurveyBean();
        SurveyEntity surveyItem = new SurveyEntity();

        survey.setThankMessage("Thank you for participating in this study");
        survey.setFindInfo(" I was able to find the information I was looking for");
        survey.setIsCompareUseful("Yes");
        survey.setEaseOfUse("Yes,the navigation is simple");
        survey.setDesignRating("It's good, need some improvements");
        survey.setIsOtherAppLikeThis("Not sure");
        survey.setWouldYouPreferTheApp("Yes");
        survey.setWouldYouRecommend("Yes");
        survey.setAnyTechnicalIssues("No");

        converter.convert(survey, surveyItem);

        assertEquals(" I was able to find the information I was looking for", surveyItem.getFindInfo());
        assertEquals("It's good, need some improvements", surveyItem.getDesignRating());
    }
}
