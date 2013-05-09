package com.hamster.display;

import com.hamster.*;
import com.hamster.mainserver.*;

import java.io.*;
import java.net.*;

/**
 * Created with IntelliJ IDEA.
 * User: Fricken Hamster
 * Date: 5/9/13
 * Time: 12:44 AM
 * 
 */
public class DisplayConnection implements Runnable
{
	DisplayServer displayServer;
	Socket socket;

	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	public static final int PING = 0;
	public static final int IMAGE_LINK = 1;
	
	public DisplayConnection(DisplayServer displayServer, Socket socket)
	{
		this.displayServer = displayServer;
		this.socket = socket;
	}

	public void recievePacket(byte[] bytearray)
	{
		DataInputStream packetStream = new DataInputStream(new BufferedInputStream( new ByteArrayInputStream(bytearray)));
		try
		{
			int id = packetStream.readByte();
			switch (id)
			{
				case PING:
				{
					consoleMessage("recieved Ping");
					sendPong();
					break;
				}
				case IMAGE_LINK:
				{
					String ss = packetStream.readUTF();
					imageServer.addLink(ss);
					consoleMessage("recieved: " + ss);

					break;
				}
			}

		} catch (IOException e)
		{
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	@Override
	public void run()
	{
		try
		{
			inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			outputStream = new DataOutputStream(new BufferedOutputStream( socket.getOutputStream()));

			while (inputStream.available() != -1)
			{
				System.out.println();
				int packetSize = inputStream.readShort();
				byte packetBuffer[] = new byte[packetSize];
				int byteRecieve = 0;
				while (byteRecieve < packetSize)
				{
					inputStream.read(packetBuffer, byteRecieve, 1);
					byteRecieve++;
				}
				recievePacket(packetBuffer);
			}


		} catch (IOException e)
		{
			debug("ERROR");
			//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void consoleMessage(String msg)
	{
		ImageServerMain.consoleMessage("IMAGECONNECTION:" + msg);
	}

	public void debug(String msg)
	{
		System.out.println("debugsubmit:" + msg);
	}
}
