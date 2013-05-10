package com.hamster.mainserver;

import com.hamster.*;
import com.hamster.display.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/9/13
 * Time: 12:41 AM
 * 
 */
public class DisplayServer implements Runnable
{

	private ServerSocket displaySocket;
	
	private int displayPort;
	private boolean listening;
	
	private List<DisplayConnection> displayList;
	

	public DisplayServer(int displayPort)
	{
		this.displayPort = displayPort;
		listening = false;
		displayList = Collections.synchronizedList(new ArrayList<DisplayConnection>());
	}

	@Override
	public void run()
	{

		try
		{
			displaySocket = new ServerSocket(displayPort);
			listening = true;
			consoleMessage("Listening");

			while (listening)
			{
				Socket socket = displaySocket.accept();
				consoleMessage("client connection from " + socket.getRemoteSocketAddress());
				DisplayConnection connection = new DisplayConnection(this, socket);
				displayList.add(connection);
				new Thread(connection).start();
			}
		} catch (IOException e)
		{
		}
		
	}
	
	public void newLink(int id, String link)
	{
		for (DisplayConnection displayConnection : displayList)
		{
			displayConnection.sendLink(id, link);
		}
	}

	public void removeSubmitConnection(DisplayConnection con)
	{
		displayList.remove(con);
	}

	public void consoleMessage(String msg)
	{
		ImageServerMain.consoleMessage("DISPLAYSERVER:" + msg);
	}
}
