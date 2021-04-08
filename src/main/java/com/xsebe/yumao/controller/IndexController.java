package com.xsebe.yumao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.yumao.model.Statement;
import com.xsebe.yumao.model.User;
import com.xsebe.yumao.service.StatementService;
import com.xsebe.yumao.utility.ShiroUtility;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequestMapping("/")
public final class IndexController {
    
    @Autowired
    private StatementService statementService;

    @RequestMapping("/index.html")
    public String openIndex(HttpServletRequest req) {
        String userAgent = req.getHeader("User-Agent");
        if (DataUtility.isNotEmptyString(userAgent, true) && userAgent.matches(".*[Mm]obile.*")) {
            return "redirect:http://u8741043.viewer.maka.im/k/HDGG8A52";
        }

        return "index";
    }

    @RequestMapping("/evidence-wang.html")
    public String evidenceWang() {
        return "evidence-wang";
    }

    @RequestMapping("/market.html")
    public String market(HttpServletRequest req) {
        boolean hasStatement = false;
        Subject currentUser = SecurityUtils.getSubject();
        if (ShiroUtility.isAuthenticated(currentUser)) {
            User loginUser = (User) currentUser.getPrincipal();
            
            try {
                List<Statement> statements = statementService.getsByUserId(loginUser, loginUser.getId());
                if (statements != null && false == statements.isEmpty()) {
                    hasStatement = true;
                }
            } catch (Throwable ex) {
            }
        }
        
        req.setAttribute("hasStatement", hasStatement);
        return "market";
    }
}