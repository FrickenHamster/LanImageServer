package com.hamster.mainserver;

import com.hamster.submit.SubmitConnection;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/8/13
 * Time: 1:42 AM
 * 
 */
public class ImageServer implements Runnable
{
	private ServerSocket submitSocket;
	private ServerSocket displaySocket;
	private int port;
	private boolean listening;
	
	private ArrayList<SubmitConnection> submitList;

	
	public ImageServer(int port)
	{
		this.port = port;
		listening = false;
		
		submitList = new ArrayList<SubmitConnection>();
	}

	@Override
	public void run()
	{
		try
		{
			submitSocket = new ServerSocket(port);
			listening = true;
			//insert console message here
			
			while (listening)
			{
				Socket socket = submitSocket.accept();
				//console message
				submitList.add(new SubmitConnection(socket));
				
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
	
}
