/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import dao.CircleDAO;
import dao.CustomerDAO;
import domains.Circle;
import domains.Customer;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.View;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
/**
 *
 * @author david
 */
@Controller
public class CircleController {
    private CircleDAO circleDAO;
    
    static int id;
    
        @RequestMapping(value = "/viewcircle={id}", method = RequestMethod.GET)
        public ModelAndView circle(@PathVariable("id") int id, ModelAndView mv) {
            ModelAndView newmv = new ModelAndView("circle");
            newmv.addObject("Circle", circleDAO.getCircle(id));
            this.id = id;
        return newmv;
        }
        
        
        
        @RequestMapping(value="/post", method = RequestMethod.POST)
        public ModelAndView post(@RequestParam(value = "msg") String msg,
                    HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException{        
            Circle circle = circleDAO.getCircle(id);
//          
//          mv.addObject("post", msg);
            int pageid = 0;
            circleDAO.postMessage(msg, pageid , (Customer)session.getAttribute("customer"), circle  );
            circle = circleDAO.getCircle(id);
            ModelAndView mv = new ModelAndView("circle");
            mv.addObject("Circle", circle);
        return mv;
    }


    public CircleDAO getCircleDAO() {
        return circleDAO;
    }

    public void setCircleDAO(CircleDAO circleDAO) {
        this.circleDAO = circleDAO;
    }
        
}
