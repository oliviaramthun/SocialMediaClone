package edu.hofstra.cs.csc017.social_network;
import java.util.ArrayList;
import java.util.List;

public class User implements Displayable {
    private String first;
    private String last;
    public String nickname;	
    public String status;
    public String town;	
    public String post; 
    public List<User> followsTheUser = new ArrayList<User>(); 
    public List<User> userIsFollowing = new ArrayList<User>();


    
    public String getFullName() {return this.first + " " + this.last;}

    //CONSTRUCTOR
    public User(String first, String last, String nickname, String status, String town){
        this.first = first;
        this.last = last;
        this.nickname = nickname;
        this.status = status;
        this.town = town;
     } 
     
        public String gettext() { //display profile info for each user 
            return "\n" + this.first + 
            " " + this.last + 
            "\nYou can call me " + this.nickname + 
            "\nStatus: "+ this.status + 
            "\nHometown: " + this.town +  
            "\nI'm followed by: " + printFollowers(this.followsTheUser) +
            "\nI follow: " + printWhoIFollow(this.userIsFollowing) +
            "\n";
        }//close gettext 

        public String printFollowers(List<User> followers){ //method to print multiple friends
            String friendPrint = ""; //start w empty string to concatinate to 
            for(User follower: followers){
                friendPrint += (follower.getFullName() + ", ");
            }
            return friendPrint; //return the concatinated string, printing all the followers 
        }
        
        public String printWhoIFollow(List<User> imFollowing){
            String whoIFollowPrint = "";
            for(User follower: imFollowing){
                whoIFollowPrint += (follower.getFullName() + ", ");
            }
            return whoIFollowPrint;
        }//close printWhoIFollow
    
}//close User Class
