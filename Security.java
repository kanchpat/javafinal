package cscie160.project;

/* Interface for Security Remote Processing 
 * 
 */
public interface Security extends java.rmi.Remote, ATMListener {
	public void handleEvent(TransactionNotification transactionInfo)
			throws java.rmi.RemoteException;

	public Permission checkAuthenticationDetails(AccountInfo accountInfo)
			throws java.rmi.RemoteException;

	public Permission checkAuthorizationDetails(AccountInfo accountInfo,
			Commands operationEvent) throws SecurityException,
			java.rmi.RemoteException;
}
