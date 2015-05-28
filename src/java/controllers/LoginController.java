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

/**
 *
 * @author david
 */
@Controller
public class LoginController {
    private CustomerDAO customerDAO;
    private CircleDAO circleDAO;
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "email") String email,
                    @RequestParam(value = "password") String password,
                    HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException{        
        Customer customer = customerDAO.authenticate(email, password);
        ModelAndView mv= new ModelAndView("index");
        
        if (customer == null) {
            mv = new ModelAndView("index");
            mv.addObject("cannotLogin", "Wrong Password or Email!");
        }
        else {
            session.setAttribute("customer", customer);
            List circles = new ArrayList();
            List<Circle> circleObjects = new ArrayList<Circle>();
            for (int i = 0; i < customer.getCircleID().size(); i++) {
                circles.add(customer.getCircleID().get(i));
                int number = customer.getCircleID().get(i);
                circleObjects.add(circleDAO.getCircle(number));
            }
            
            mv.addObject("circleList", circles); 
            mv.addObject("circleObjects", circleObjects);
        }
        return mv;
    }

    public CircleDAO getCircleDAO() {
        return circleDAO;
    }

    public void setCircleDAO(CircleDAO circleDAO) {
        this.circleDAO = circleDAO;
    }

    
    
    
    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    
    
    @RequestMapping(value="/logout")
    public ModelAndView logout(HttpSession session){
        ModelAndView mv = new ModelAndView("index");
        
        session.removeAttribute("currentPerson");
        
        return mv;
    }

   
}
