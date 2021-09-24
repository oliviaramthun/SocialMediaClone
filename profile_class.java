import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NoSuchFileException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


import edu.hofstra.cs.csc017.social_network.Displayable;
import edu.hofstra.cs.csc017.social_network.User;
import edu.hofstra.cs.csc017.social_network.Post;


	public class profile_class { 

		
		public static void display(Displayable thingToDisplay) {
			System.out.println(thingToDisplay.gettext());
			} //call get text function for each user profile

//************************************ Main *****************************************************************************
		public static void main(String[] arguments) {

			List<User> CurrentUsers = new ArrayList<User>();
			List<Post> allPostsByAllUsers = new ArrayList<Post>();

			loadUserInfoFile("People.txt", CurrentUsers);
			loadRelationshipInfoFile("Relationships.txt", CurrentUsers);
			loadPostInfoFile("Posts.txt", CurrentUsers, allPostsByAllUsers);
			displayUserTimelines(CurrentUsers, allPostsByAllUsers);
					
		}//end main 

//************************************* Start People.txt **************************************************************	

		public static void loadUserInfoFile(String Filename, List<User> CurrentUsers) {

			
			try { 
				Path ToFile = FileSystems.getDefault().getPath(Filename);

			
				List<String> People = Files.readAllLines(ToFile);
				 
				
				for(String user: People){ 
					String[] PeopleInfo = user.split("\\|"); 
					
					if(PeopleInfo.length == 5){
						CurrentUsers.add(new User(PeopleInfo[0],PeopleInfo[1],PeopleInfo[2],PeopleInfo[3],PeopleInfo[4]));
					}//close if 
					
					else{
						System.out.println("So sorry, it looks like the line\n" 
						+ Arrays.toString(PeopleInfo)
						+ " is formatted incorrectly...");
					}//close else
				}//close for loop

				}//close try block

				
				catch(NoSuchFileException MissingUserFile){ 
					System.out.println("The file " + MissingUserFile.getFile() + "could not be accessed..." +
					"\nProgram will terminate due to a lack of user data");
					System.exit(1); //value of non zero as the program did not successfuly run 
				} 
				catch(IOException exception){
					System.out.println("Uh oh, something went funky when I tried to read the People.txt file...\n" +
					exception.getMessage());
					System.exit(1);

				}//close catch block 

		} //end loadPeopleInfoFile

		

//************************************* Start Relationships.txt ********************************************************* 
		
	public static void loadRelationshipInfoFile(String Filename, List<User> CurrentUsers){
			try{ 

				Path RelationshipPath = FileSystems.getDefault().getPath("Relationships.txt");
				
				List<String> relationshipInfo = Files.readAllLines(RelationshipPath);
				
				
				for(String CurrentLine: relationshipInfo) { 
					String[] Person = CurrentLine.split("\\|"); 

					if( Person.length == 4){ 
						String UserName = Person[0] + " " + Person[1];
						//USER FIRST AND LAST NAME 
						String FollowName = Person[2] + " " + Person[3];
						//FOLLOWER FIRST AND LAST NAME 
						User selectedUser = null;
						//make placeholder value for user of type User
						User selectedFollower = null;
						//placeholder variable for follower of type User


						for(User user: CurrentUsers){
							
							if(user.getFullName().equals(UserName)){
								selectedUser = user;
							}
							else if(user.getFullName().equals(FollowName)){
								selectedFollower = user;
								}//close elseif
							}//close for loop
						

						if(selectedFollower != null && selectedUser !=null){
							selectedUser.followsTheUser.add(selectedFollower); //list of users who follow the loggedInUser 
							selectedFollower.userIsFollowing.add(selectedUser); //list of users the loggedInUser follows
						}//close if


						if(selectedUser == null ){
							System.out.println("So sorry... " + UserName + 
							" was not found, or was formatted incorrectly in the user file...");
							}

						else if(selectedFollower == null){
							System.out.println("So sorry... " + FollowName + 
							" was not found, or was formatted incorrectly in the user file...");
							}
					}//close if ( length==4 )


					else if(Person.length > 4){
						System.out.println("It seems the relationships file was not formatted properly," + 
						"the improperly formatted friends will be omitted...");
						}
				}//close for loop
						
				
			} //close try block for Relationships.txt

			catch(NoSuchFileException MissingRelationshipsFile){ 
				System.out.println("The file " + MissingRelationshipsFile.getFile() + 
				" could not be accessed..." +
				"\nRelationships have been omitted from user profiles...");
				 } 
			 catch(IOException exception){
				System.out.println("Uh oh, something went funky when I tried to read the Relationships.txt file...\n" +
				exception.getMessage());
				System.exit(1);
				}
		}//end loadRelationshipInfoFile


//********************************* Start Post.txt ************************************************************************

		public static void loadPostInfoFile(String filename, List<User> CurrentUsers, List<Post> allPostsByAllUsers){
			try{
				Path PostPath = FileSystems.getDefault().getPath("Posts.txt");
							
				List<String> AllPosts = Files.readAllLines(PostPath);
				

				for(String CurrentLine: AllPosts) { //currentLine == iterator variable 

					String[] PostAndAuthor = CurrentLine.split("\\|"); 

					if( PostAndAuthor.length == 3){ //ensure there is not more than one author and post per line 
						String UserName = PostAndAuthor[0] + " " + PostAndAuthor[1];
						//Users first and last name
						String UserPost = PostAndAuthor[2];
						//Users post

						User authorFoundInUserNetwork = null; 

						for(User Author: CurrentUsers){
							if(Author.getFullName().equals(UserName)){
								authorFoundInUserNetwork = Author;
							}
						}//close for 

							
						if(authorFoundInUserNetwork != null){
								Post newUserPost = new Post(UserPost, UserName);
								allPostsByAllUsers.add(newUserPost);
						}
						else{
							System.out.println("Hmm, I couldn't find " + UserName + " in the network! Check your People.txt file...");
						}
						

					}//close length if 
					else{
						System.out.println("Hmmm, It seems the post.txt file was formatted incorrectly...");
						}
				
					}//close for loop

				}//close try block 


				catch(NoSuchFileException MissingPostsFile){ 
					System.out.println("The file " + MissingPostsFile.getFile() + 
					" could not be accessed..." +
					"\nPosts have been omitted from the timeline...");
					} 
				catch(IOException exception){
					System.out.println("Uh oh, something went funky when I tried to read the Posts.txt file...\n" +
					exception.getMessage());
					System.exit(1);
					}
					
		}//end loadPostInfoFile

//************************************ Printing User Specific Timelines *********************************************

		public static void displayUserTimelines(List<User> CurrentUsers, List<Post> allPostsByAllUsers){

			User loggedInUser = CurrentUsers.get(3);

			System.out.println("\nYou are currently logged in as: " + 
								loggedInUser.getFullName() + "\n");
			
			display(loggedInUser);

			System.out.println("\n\t\t\t---- Hi " + loggedInUser.getFullName() + 
							   ", heres your timeline! ----\n");

			for(Post followingUsersPost: allPostsByAllUsers){

				for(User whoLoggedInFollows: loggedInUser.userIsFollowing){

					if(followingUsersPost.getAuthor().equals(whoLoggedInFollows.getFullName())){
						display(followingUsersPost);
					}
				}
			}

			//*************************** Determine Reccomended User Timeline ************************************************

			List<User> postsOfEveryFollowedUser = new ArrayList<>(); //contains duplicate posts & post of loggedInUser 

			System.out.println("\n\t\t\t---- Hi " + loggedInUser.getFullName() +
								", heres a timeline of reccomended posts for you! ---- \n\n");


			for(User userInFollowingListofLoggedInUser: loggedInUser.userIsFollowing){

				for(User potentialReccomendedUser: CurrentUsers){
					(potentialReccomendedUser.followsTheUser).stream()
					.filter(suggestedUser -> suggestedUser == userInFollowingListofLoggedInUser)
					.forEach(userInNetwork -> postsOfEveryFollowedUser.add(potentialReccomendedUser));
				}

			}

			List<User> reccomendedTimeline = new ArrayList<>(); 
		
			//************************* Eliminate duplicates, and posts of LoggedInUser ************************************


			for(User user : postsOfEveryFollowedUser) {
				if(!reccomendedTimeline.contains(user)//ensures repeated posts are not added to the RT
				   && !loggedInUser.userIsFollowing.contains(user)//ensures no posts the loggedInUser follows added to the RT
				   && !loggedInUser.equals(user)){//ensures the loggedInUsers own posts arent added to RT  
					   reccomendedTimeline.add(user);
				}
			}
		
			///************************* Display the reccomended timeline *************************************************
			
			for(Post userReccomendedPost: allPostsByAllUsers){
				for(User user: reccomendedTimeline){
					if(userReccomendedPost.getAuthor().equals(user.getFullName())){
						display(userReccomendedPost);
					}

				}

			}

		}//end displayUserTimelines

	}//end profile class 

		

	