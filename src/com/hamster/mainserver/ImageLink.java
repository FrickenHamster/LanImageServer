package com.hamster.mainserver;/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/9/13
 * Time: 12:25 AM
 * 
 */
public class ImageLink 
{
	private String imageLink;
	private int id;

	public ImageLink(String imageLink, int id)
	{
		this.imageLink = imageLink;
		this.id = id;
		
	}

	public String getImageLink()
	{
		return imageLink;
	}
}
