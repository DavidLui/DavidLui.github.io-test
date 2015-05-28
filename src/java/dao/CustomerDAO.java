/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Desktop
 */

import domains.Circle;
import domains.Customer;
import domains.Posts;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



public class CustomerDAO {
    private JdbcTemplate jdbcTemplate;
    static Customer cust;
    public CustomerDAO() {
        cust = new Customer();
    }
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    static String pass;
    public Customer authenticate(String email, String pw){
        String Query = "SELECT UserId, SSN, Sex, EmailId, DOB, Rating\n" +
                        "FROM user\n" +
                        "WHERE EmailId = \"" + email + "\" AND UserId = " + pw;
        pass = pw;
        Customer customer = this.jdbcTemplate.queryForObject(
                Query, new RowMapper<Customer>(){
                    @Override
                    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        if (rs.getString("UserId").matches(pass)) {
                            cust = new Customer();
                            cust.setCustomerID(rs.getInt("UserId"));
                            cust.setSSN(rs.getInt("SSN"));
                            String sex = rs.getString("Sex");
                            cust.setSex(sex.charAt(0));
                            cust.setDOB("DOB");
                            cust.setRating(rs.getInt("Rating"));
                           
                            //table = person for address
                            getAddress(cust.getSSN());
                            
                            //table = circlemembership for circle ids
                            cust.setCircleID(getCircles(cust.getCustomerID()));                            return cust; 
                        }
                        else {
                            return null;
                        }
                        
                    }
                }
        );
        return customer;
    }
        
     
    public List getCircles(int userId) {
        String q = "SELECT CircleID\n" +
        " FROM circlemembership\n" +
        " WHERE UserID = " + userId;
        
        List ray = this.jdbcTemplate.queryForObject(
                q, new RowMapper<List>(){
                    @Override
                    public List mapRow(ResultSet rs, int rowNum) throws SQLException {
                        List roy = new ArrayList();
                        roy.add(rs.getInt(1));
                        while(rs.next()) {
                            roy.add(rs.getInt(1));
                            
                        }
                        return roy;
                    }
                });
        
        return ray;
    }
    public void getAddress(int SSN) {
        String q = "SELECT LastName,FirstName, Address, City, State, ZipCode, Telephone\n" +
                                        "FROM person\n" +
                                        "WHERE SSN = " + SSN;
     
        this.jdbcTemplate.queryForObject(
                q, new RowMapper<String>(){
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        
                        cust.setAddress(rs.getString("Address"));
                        cust.setCity(rs.getString("City"));
                        cust.setLastName(rs.getString("LastName"));
                        cust.setFirstName(rs.getString("FirstName"));
                        cust.setState(rs.getString("State"));
                        cust.setZipcode(rs.getInt("ZipCode"));
                        cust.setTelephone(rs.getString("Telephone"));
                        return "";
                    }
                });
        
    }
}
