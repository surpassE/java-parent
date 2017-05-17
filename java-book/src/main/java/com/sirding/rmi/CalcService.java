package com.sirding.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CalcService extends Remote {

	int add(int a, int b) throws RemoteException;
}
