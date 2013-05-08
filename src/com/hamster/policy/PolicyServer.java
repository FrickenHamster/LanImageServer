package com.hamster.policy;

import com.hamster.ImageServerMain;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/7/13
 * Time: 1:27 PM
 * 
 */
public class PolicyServer implements Runnable
{

	public static final String POLICY_REQUEST = "<policy-file-request/>";
	public static final String POLICY_XML =
			"<?xml version=\"1.0\"?>"
					+ "<cross-domain-policy>"
					+ "<allow-access-from domain=\"*\" to-ports=\"*\" />"
					+ "</cross-domain-policy>";

	private int port;
	private ServerSocket serverSocket;
	private boolean listening;
	
	public PolicyServer( int port)
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
			ImageServerMain.consoleMessage("Policy Server Listening");
			
			while (listening)
			{
				Socket socket = serverSocket.accept();
				consoleMessage("client connection from " + socket.getRemoteSocketAddress());
				PolicyServerConnection connection = new PolicyServerConnection(socket);
				new Thread(connection).start();
			}
					
		} catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
	
	public void consoleMessage(String msg)
	{
		ImageServerMain.consoleMessage(msg);
	}
}
