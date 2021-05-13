/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.converters;

import co.uk.city.london.beans.SurveyBean;
import co.uk.city.london.entities.SurveyEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author mar_9
 */
@Component
public class SurveyBeanToEntityConverter {

    public void convert(SurveyBean bean, SurveyEntity surveyEntity) {
        surveyEntity.setAnyTechnicalIssues(bean.getAnyTechnicalIssues());
        surveyEntity.setDesignRating(bean.getDesignRating());
        surveyEntity.setEaseOfUse(bean.getEaseOfUse());
        surveyEntity.setFindInfo(bean.getFindInfo());
        surveyEntity.setIsCompareUseful(bean.getIsCompareUseful());
        surveyEntity.setWouldYouPreferTheApp(bean.getWouldYouPreferTheApp());
        surveyEntity.setWouldYouRecommend(bean.getWouldYouRecommend());
        surveyEntity.setDesignRating(bean.getDesignRating());
        surveyEntity.setAgreeToProceed(bean.getAgreeToProceed());
        surveyEntity.setCompOtherSites(bean.getCompOtherSites());
        surveyEntity.setConfirmBeInformed(bean.isConfirmBeInformed());
        surveyEntity.setConfirmBePart(bean.isConfirmBePart());
        surveyEntity.setConfirmDataWithdraw(bean.isConfirmDataWithdraw());
        surveyEntity.setConfirmParticipentInfo(bean.isConfirmParticipentInfo());
        surveyEntity.setConfirmRecording(bean.isConfirmRecording());
        surveyEntity.setConfirmVoluntary(bean.isConfirmVoluntary());
        surveyEntity.setDiffParties(bean.getDiffParties());
        surveyEntity.setEasyParties(bean.getEasyParties());
        surveyEntity.setShareName(bean.getShareName());
        surveyEntity.setSimilerWebSites(bean.getSimilerWebSites());
        surveyEntity.setSpentTime(bean.getSpentTime());
        surveyEntity.setToken(bean.getToken());
        surveyEntity.setTypeOfCourse(bean.getTypeOfCourse());
        surveyEntity.setUsageOfOtherSite(bean.getUsageOfOtherSite());
        surveyEntity.setUsefullAspects(bean.getUsefullAspects());
        surveyEntity.setWeakestAspect(bean.getWeakestAspect());
    }
}
