package com.hamster.submit;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/8/13
 * Time: 1:57 AM
 * 
 */
public class SubmitConnection implements Runnable
{
	
	private Socket socket;

	public SubmitConnection(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		DataOutputStream outputStream = new DataOutputStream( socket.getOutputStream( ));
	}
	
	public void recievePacket()
	{
		
	}
}
