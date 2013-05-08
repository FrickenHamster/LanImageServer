package com.hamster.mainserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/8/13
 * Time: 1:42 AM
 * 
 */
public class ImageServer implements Runnable
{
	private ServerSocket serverSocket;
	private int port;
	private boolean listening;

	
	public ImageServer(int port)
	{
		this.port = port;
		listening = false;
		
		
	}

	@Override
	public void run()
	{
		try
		{
			serverSocket = new ServerSocket(port);
			listening = true;
			//insert console message here
			
			while (listening)
			{
				Socket socket = serverSocket.accept();
				//console message
				
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
	
}
