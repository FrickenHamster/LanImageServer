package com.hamster;

import com.hamster.mainserver.*;
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
	
	public static int POLICY_PORT = 12242;
	public static int UPLOAD_PORT = 12243;
	public static int DISPLAY_PORT = 12244;
	
	public ImageServerMain()
	{
		
	}
	public static void main( String[] args )
	{
		new Thread(new PolicyServer(POLICY_PORT)).start();
		new Thread(new ImageServer(UPLOAD_PORT, DISPLAY_PORT)).start();
	}
	
	public static void consoleMessage(String msg)
	{
		System.out.println("SYS:" + msg);
	}
	
}
