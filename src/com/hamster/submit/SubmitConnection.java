package com.hamster.submit;

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
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
