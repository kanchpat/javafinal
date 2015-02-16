package cscie160.project;

/* This class is the registered Client Listener who produces information to the client
 * 
 */
import java.rmi.RemoteException;

public class ClientInfo extends java.rmi.server.UnicastRemoteObject implements
		ATMListener {
	protected ClientInfo() throws RemoteException {
		super();
	}

	public void handleEvent(TransactionNotification transactionNotification)
			throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(transactionNotification.toString());
	}
}
