package com.hamster;

import com.hamster.policy.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/7/13
 * Time: 1:26 PM
 * 
 */
public class ImageServerMain 
{
	
	public ImageServerMain()
	{
		
	}
	public static void main( String[] args )
	{
		new Thread(new PolicyServer(22423)).start();
	}
	
	public static void consoleMessage(String msg)
	{
		System.out.println("SYS:" + msg);
	}
	
}
