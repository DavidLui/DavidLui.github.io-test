package domains;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author david
 */
public class Circle implements Serializable{
    private List CustomerID;
    private int id;
    //each page has multiple posts
    private List posts;
    private String name;
    private String type;
    private int owner;
    public Circle(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    
    public List getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(List CustomerID) {
        this.CustomerID = CustomerID;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getPosts() {
        return posts;
    }

    public void setPosts(List posts) {
        this.posts = posts;
    }
    
    

    
}
