/** Interface for Bank
 * 
 */
package cscie160.project;

public interface Bank extends java.rmi.Remote, ATMListener {
	public void deposit(TransactionNotification transactionNotification)
			throws ATMException, java.rmi.RemoteException;

	public void withdraw(TransactionNotification transactionNotification)
			throws ATMException, java.rmi.RemoteException;

	public Float getBalance(TransactionNotification transactionNotification)
			throws ATMException, java.rmi.RemoteException;

	public void transferAmount(TransactionNotification transactionNotification,
			int toAccount) throws ATMException, java.rmi.RemoteException;

	public void handleEvent(TransactionNotification transactionInfo)
			throws java.rmi.RemoteException;
}