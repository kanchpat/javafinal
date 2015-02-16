package cscie160.project;

/* Interface for ATMListener for Remote Processing
 * 
 */
public interface ATMListener extends java.rmi.Remote {
	public void handleEvent(TransactionNotification transactionInfo)
			throws java.rmi.RemoteException;
}
