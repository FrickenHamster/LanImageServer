package com.hamster.mainserver;

import com.hamster.*;
import com.hamster.submit.SubmitConnection;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

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
	
	private int uploadPort;
	private int displayPort;
	private boolean listening;
	
	private List<SubmitConnection> submitList;
	private List<ImageLink> linkList;
	
	private int linkCounter;

	private Thread displayServerThread;
	
	public ImageServer(int uploadPort, int displayPort)
	{
		this.uploadPort = uploadPort;
		this.displayPort = displayPort;
		listening = false;
		
		submitList = Collections.synchronizedList(new ArrayList<SubmitConnection>());
		linkList = Collections.synchronizedList(new ArrayList<ImageLink>());
		linkCounter = 0;
		
		displayServerThread = new Thread(new DisplayServer(displayPort));
	}

	@Override
	public void run()
	{
		try
		{
			submitSocket = new ServerSocket(uploadPort);
			listening = true;
			consoleMessage("Listening");
			displayServerThread.start();
			
			while (listening)
			{
				Socket socket = submitSocket.accept();
				consoleMessage("client connection from " + socket.getRemoteSocketAddress());
				SubmitConnection connection = new SubmitConnection(this, socket);
				submitList.add(connection);
				new Thread(connection).start();
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
	
	public int addLink(String link)
	{
		ImageLink imageLink = new ImageLink(link, linkCounter);
		linkList.add(imageLink);
		return linkCounter++;
	}
	
	public void removeSubmitConnection(SubmitConnection con)
	{
		submitList.remove(con);
	}

	public void consoleMessage(String msg)
	{
		ImageServerMain.consoleMessage("IMAGESERVER:" + msg);
	}
	
}
