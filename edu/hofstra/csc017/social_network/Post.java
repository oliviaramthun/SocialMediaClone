package edu.hofstra.cs.csc017.social_network;


public class Post implements Displayable{
		
		private String blurb; //blog post for profile 
		private String author;

			public Post(String blurb, String author){
				this.blurb = blurb;
				this.author = author;
				}//end Post

			public String gettext() {
				return "\n\t" + author + ": " + 
				"\n\t" + blurb + "\n";
				}//end gettext 
				
			public String getAuthor() {
				return author;
			}
	}

