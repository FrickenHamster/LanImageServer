package com.hamster.submit;

import com.hamster.*;
import com.hamster.mainserver.*;

import java.io.*;
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
	public String TAG;
	
	private ImageServer imageServer;
	private Socket socket;
	
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	public static final int PING = 0;
	public static final int IMAGE_LINK = 1;

	public SubmitConnection(ImageServer imageServer, Socket socket)
	{
		this.imageServer = imageServer;
		this.socket = socket;
		
		TAG = "Connection" + socket.getRemoteSocketAddress();
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
	
	public void sendPong()
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		output.write(PING);
		byte[] ba = output.toByteArray();
		sendPacket(ba);
	}
	
	public void sendPacket(byte[] ba)
	{
		try
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DataOutputStream dd = new DataOutputStream( bout );
			dd.writeShort( ba.length );
			dd.write( ba );
			byte[] bb = bout.toByteArray();

			outputStream.write(bb);
			outputStream.flush();
		}
		catch( Exception e )
		{
			debug( e.getMessage() );
			closeConnection();
		}
	}

	protected void closeConnection()
	{
		try {
			this.outputStream.close();
			this.inputStream.close();
			this.socket.close();
			imageServer.removeSubmitConnection(this);
			consoleMessage("connection closed");
		}
		catch (Exception e) {
			debug("Exception (closing): " + e.getMessage());
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
