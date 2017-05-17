package com.sirding.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.sirding.rmi.CalcService;

public class CalcServiceImpl extends UnicastRemoteObject implements CalcService{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CalcServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public int add(int a, int b) throws RemoteException {
		return (a + b);
	}

}
