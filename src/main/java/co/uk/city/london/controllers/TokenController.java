/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.uk.city.london.controllers;

import co.uk.city.london.beans.SurveyBean;
import co.uk.city.london.daos.TokenDao;
import co.uk.city.london.entities.TokenEntiyty;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static java.util.stream.Collectors.*;

/**
 *
 * @author mar_9
 */
@Controller
public class TokenController {

    @Autowired
    private TokenDao tokendao;
    
    @GetMapping("/tokens")
    public String loadTokensPage(Model model) {
         List<TokenEntiyty> tokens = tokendao.findAll();
         List<TokenEntiyty> notUsedTokens = tokens.stream().filter(t -> !t.isIsUsed()).collect(toList());
         notUsedTokens = notUsedTokens.stream().filter(t -> !isThereUsed(t, tokens)).collect(toList());
         model.addAttribute("tokens", notUsedTokens);
        
        return "tokens";
    }
    
    
    private boolean isThereUsed(TokenEntiyty token,   List<TokenEntiyty> tokens) {
        return tokens.stream().filter(t -> t.isIsUsed() && t.getToken().equals(token.getToken())).findAny().isPresent();
    }
    
    @GetMapping("/generateToken")
    public String loadSurvey(Model model) {
        TokenEntiyty token = new TokenEntiyty();
        token.setToken(UUID.randomUUID().toString().toUpperCase());
        token.setIsUsed(false);
        
        tokendao.save(token);
        
         List<TokenEntiyty> tokens = tokendao.findAll();
         List<TokenEntiyty> notUsedTokens = tokens.stream().filter(t -> !t.isIsUsed()).collect(toList());
         notUsedTokens = notUsedTokens.stream().filter(t -> !isThereUsed(t, tokens)).collect(toList());
         model.addAttribute("tokens", notUsedTokens);

         
        return "tokens";
    }
    
    
}
