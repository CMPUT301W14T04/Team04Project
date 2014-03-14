package com.example.geoloc;


public class Favourites
{

	/** 
	 * @uml.property name="comments"
	 * @uml.associationEnd inverse="favourites:com.example.team04project.Comments"
	 */
	private CommentMetaData comments;

	/** 
	 * Getter of the property <tt>comments</tt>
	 * @return  Returns the comments.
	 * @uml.property  name="comments"
	 */
	public CommentMetaData getComments()
	
	{
		return comments;
	}

	/** 
	 * Setter of the property <tt>comments</tt>
	 * @param comments  The comments to set.
	 * @uml.property  name="comments"
	 */
	public void setComments(CommentMetaData comments)
	
	{
		this.comments = comments;
	}
	public void saveFavourite(){
		
	}
}

