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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



public class CircleDAO {
    private JdbcTemplate jdbcTemplate;
    
    public CircleDAO() {
        
    }
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public Circle getCircle(int circleID){
        Circle circle = this.jdbcTemplate.queryForObject(
                "SELECT * FROM circle" +
                " WHERE CircleId="+circleID+";", new RowMapper<Circle>(){
                    @Override
                    public Circle mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Circle circle = new Circle();
                        circle.setId(rs.getInt("CircleId"));
                        List<Integer> customers = getCustomerId(circle.getId());
                        
                        circle.setCustomerID(customers);
                        
                        List<Posts> posts = getPosts(circle.getId());
                        circle.setName(rs.getString("Name"));
                        circle.setType(rs.getString("Type"));
                        circle.setPosts(posts);
                        
                        return circle;
                    }
                }
        );
        return circle;
    }
//    SELECT PostId FROM contains
//    WHERE PageId = (
//	SELECT PageId FROM hasa
//	WHERE CircleId = 8001
//    );
    public List<Posts> getPosts(int circleId) {
        String q = "SELECT PageId FROM hasa\nWHERE CircleId = " + circleId;
        String query = "SELECT PostId FROM contains"
              + " WHERE PageId = (" + q + ");";
        
        List<Posts> posts = this.jdbcTemplate.query(
                query,
                new RowMapper<Posts>(){
                    @Override
                    public Posts mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Posts post =  new Posts(); 
                        post.setPostID(rs.getInt("PostId"));
                        setPostData(post);
                        post.setComments(getComments(post.getPostID()));
                        
                        
                        
                        return post;
                       
                    }
                }
        );
        
        
        
        
        return posts;
    }
    
//    SELECT a.UserId, b.* 
//    FROM writes a, post b
//    WHERE a.PostId = 20112 AND b.PostId = 20112
    public Posts setPostData(Posts post){
        
        String query =
                
                "SELECT a.UserId, b.* "
                + "\nFROM writes a, post b "
                + "\nWHERE a.PostId = " + post.getPostID()+" AND b.PostId = " + post.getPostID();
                
               
        Posts post2 = this.jdbcTemplate.queryForObject(
                query, new RowMapper<Posts>(){
                    @Override
                    public Posts mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Posts posts = new Posts();
                        posts.setAuthor(rs.getInt("UserId"));
                        posts.setDate(rs.getDate("Date").toString());
                        posts.setContent(rs.getString("Content"));
                        
                        return posts;
                    }
                }
        );
        post.setPosterName(getName(post2.getAuthor()));
        post.setAuthor(post2.getAuthor());
        post.setDate(post2.getDate());
        post.setContent(post2.getContent());
        
        return post;
    }
    public String getName(int id) {
        String q = "SELECT FirstName, LastName\n" +
                    "FROM person\n" +
                    "WHERE SSN = (\n" +
                    "	SELECT SSN\n" +
                    "    FROM user\n" +
                    "    WHERE UserId = " + id + "\n" +
                    ")";
        
        String name = this.jdbcTemplate.queryForObject(
                q, new RowMapper<String>(){
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                  
                        return rs.getString("FirstName") + " " + rs.getString("LastName");
                    }
                }
        );
        
        return name;
    }
    
    
    
    
    
//    SELECT CommentId
//	FROM gets
//	WHERE PostId = 20111
    public List<String> getComments(int postId) {
        String query = 
                
                "SELECT CommentId" +
                "\nFROM gets" +
                "\nWHERE PostId = "+ postId;
        
        List<Integer> commentIds = this.jdbcTemplate.query(
                query,
                new RowMapper<Integer>(){
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        int commentId = rs.getInt("CommentId");
                        return commentId;
                    }
                }
        );
        List<String> comments = null;
        for (int id: commentIds) {
            String q = "SELECT a.UserId, b.Content, b.Date\n" +
                        "\nFROM makes a, comment b\n" +
                        "\nWHERE a.CommentId ="+ id+" AND b.CommentId ="+id;
            comments = this.jdbcTemplate.query(
                q,
                new RowMapper<String>(){
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String s  = "";
                        s+= rs.getInt("UserId") + " " + rs.getDate("Date").toString()+"\n " 
                                + rs.getString("Content");
                        
                        return s;
                    }
                }
            );
            
            
            
            
            
        }
        
        return comments;
    }
    
    
    public List<Integer> getCustomerId(int circleId){
        String query = "SELECT * FROM circlemembership"
              + " WHERE CircleID="+circleId+";";
        List<Integer> custIds = this.jdbcTemplate.query(
                query,
                new RowMapper<Integer>(){
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt("UserID");
                    }
                }
        );
        return custIds;
    }

    public void postMessage(String msg, int pageid, Customer customer, Circle circle) {
        int postID= getMaxPost();
        
        this.jdbcTemplate.update("insert into post(PostId, Content, Date, Commentcount)"
                + "values (?,?,?,?)", postID+1, msg, "2012-2-2", 0);
        this.jdbcTemplate.update("insert into writes(UserId, PostId)"
                + "values (?,?)", customer.getCustomerID(), postID+1);
        
        pageid = getPageId(circle.getId());
        
        
        this.jdbcTemplate.update("insert into contains(PageId, PostId)"
                + "values (?,?)", pageid, postID+1);
        
        this.jdbcTemplate.update("update page set Postcount = Postcount+1 where PageId = ?" ,pageid);
        
    
    
    }   
    public int getPageId(int circleId) {
        String query = "SELECT PageId FROM hasa"
                + "\nWHERE CircleId = " + circleId;
        int pageid = this.jdbcTemplate.queryForObject(
                query, new RowMapper<Integer>(){
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getInt("PageId");
                    }
                }
        );
        return pageid;
        
    }
    
    public int getMaxPost() {
        String query = "SELECT MAX(PostId) FROM writes";
            int postNum = this.jdbcTemplate.queryForObject(
                query, new RowMapper<Integer>(){
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                  
                        return rs.getInt("MAX(PostId)");
                    }
                }
        );
        return postNum;
    }

    
}
