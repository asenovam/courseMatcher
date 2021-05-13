/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.beans.SurveyBean;
import co.uk.city.london.converters.SurveyBeanToEntityConverter;
import co.uk.city.london.daos.SurveyDao;
import co.uk.city.london.daos.TokenDao;
import co.uk.city.london.entities.SurveyEntity;
import co.uk.city.london.entities.TokenEntiyty;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author mar_9
 */
@Controller
public class SurveyController {

    @Autowired
    private SurveyDao surveyDao;
    
    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private SurveyBeanToEntityConverter surveyBeanToEntityConverter;
    
    @GetMapping("/survey")
    public String loadSurvey(Model model, @RequestParam(value = "token", required = false) String token) {
         SurveyBean surveyBean = new SurveyBean();
       
         
        if (token == null || token.isEmpty()) {
            surveyBean.setErrorMessage("Please access this survey through the link provided in your email !");
        } else {
            List<TokenEntiyty> tokenFromDb = tokenDao.findByToken(token);
            if (tokenFromDb == null || tokenFromDb.isEmpty()) {
                surveyBean.setErrorMessage("Please access this survey through the link provided in your email!");
            } else if (tokenFromDb.stream().filter(t -> t.isIsUsed()).findAny().isPresent()) {
                List<SurveyEntity> surveyByToken = surveyDao.findByToken(token);
                if (surveyByToken != null && !surveyByToken.isEmpty()) {
                    surveyBean.setErrorMessage("That token has already used. No modifications are acepted!");
                    surveyBean.setThankMessage("Thank you for your survey taken!");
                }
           }
        }
        
        surveyBean.setToken(token);
        model.addAttribute("survey", surveyBean);

        return "survey";
    }

    @PostMapping("/survey")
    public String saleSurvey(@ModelAttribute SurveyBean surveyBean, Model model, RedirectAttributes attr) {
        if (surveyBean.getToken() == null || surveyBean.getToken().isEmpty()) {
              surveyBean.setErrorMessage("You survey was NOT submitted. Please access this survey through the link provided in your email !");
              model.addAttribute("survey", surveyBean);
              return "survey";
        }
        List<TokenEntiyty> tokenFromDb = tokenDao.findByToken(surveyBean.getToken());
        if (tokenFromDb == null || tokenFromDb.isEmpty()) {
            surveyBean.setErrorMessage("You survey was NOT submitted. Please access this survey through the link provided in your email!");
             model.addAttribute("survey", surveyBean);
            return "survey";
        }
        TokenEntiyty tokenEntity = tokenFromDb.get(0);
        if ( tokenFromDb.stream().filter(t -> t.isIsUsed()).findAny().isPresent()) {
            List<SurveyEntity> surveyByToken = surveyDao.findByToken(surveyBean.getToken());
           if (surveyByToken != null && !surveyByToken.isEmpty()) {
                surveyBean.setErrorMessage("That token has already used. No modifications are acepted!");
                model.addAttribute("survey", surveyBean);
                return "survey";
           }
        }
        
        if (surveyBean.getTypeOfCourse()== null || surveyBean.getTypeOfCourse().isEmpty()
                || surveyBean.getSpentTime()== null || surveyBean.getSpentTime().isEmpty()
                || surveyBean.getEaseOfUse()== null || surveyBean.getEaseOfUse().isEmpty()
                || surveyBean.getFindInfo() == null || surveyBean.getFindInfo().isEmpty()
                || surveyBean.getUsefullAspects()== null || surveyBean.getUsefullAspects().isEmpty()
                || surveyBean.getIsCompareUseful()== null || surveyBean.getIsCompareUseful().isEmpty()
                || surveyBean.getEasyParties()== null || surveyBean.getEasyParties().isEmpty()
                || surveyBean.getWeakestAspect()== null || surveyBean.getWeakestAspect().isEmpty()
                || surveyBean.getUsageOfOtherSite()== null || surveyBean.getUsageOfOtherSite().isEmpty()
                || surveyBean.getCompOtherSites()== null || surveyBean.getCompOtherSites().isEmpty()
                || surveyBean.getWouldYouRecommend()== null || surveyBean.getWouldYouRecommend().isEmpty()
                || surveyBean.getDiffParties()== null || surveyBean.getDiffParties().isEmpty()
                || surveyBean.getAnyTechnicalIssues()== null || surveyBean.getAnyTechnicalIssues().isEmpty()
                || surveyBean.getIsOtherAppLikeThis()== null || surveyBean.getIsOtherAppLikeThis().isEmpty()) {
            surveyBean.setErrorMessage("Fill all mandatory fields!");
           
            model.addAttribute("survey", surveyBean);

            return "survey";
        }
        
        if (!surveyBean.isConfirmBeInformed() || !surveyBean.isConfirmBePart() || !surveyBean.isConfirmDataWithdraw()
               || !surveyBean.isConfirmParticipentInfo() || !surveyBean.isConfirmRecording() || !surveyBean.isConfirmVoluntary()
                || (surveyBean.getAgreeToProceed() == null || surveyBean.getAgreeToProceed().equals("No"))) {
            surveyBean.setErrorMessage("Please tick all the field in the consent form !");
            model.addAttribute("survey", surveyBean);
            
            return "survey";
        }

        SurveyEntity surveyEntity = new SurveyEntity();
        this.surveyBeanToEntityConverter.convert(surveyBean, surveyEntity);

        this.surveyDao.save(surveyEntity);

       tokenEntity.setIsUsed(true);
        this.tokenDao.save(tokenEntity);
        
        surveyBean.setThankMessage("Thank you for your survey taken!");

        
        model.addAttribute("survey", surveyBean);

        return "redirect:/survey?token=" + surveyBean.getToken();
    }
    
    @GetMapping("/submittedsurveys")
    public String loadSurveys(Model model) {
         List<SurveyEntity> surveyEntitys = surveyDao.findAll();
         model.addAttribute("surveys", surveyEntitys);

        return "submittedsurveys";
    }
}
