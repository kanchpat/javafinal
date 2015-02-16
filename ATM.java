/** Interface for ATM 
 * 
 */
package cscie160.project;

public interface ATM extends java.rmi.Remote, ATMListener {
	public void deposit(AccountInfo accountInfo, float amount)
			throws java.rmi.RemoteException, SecurityException, ATMException;

	public void withdraw(AccountInfo accountInfo, float amount)
			throws java.rmi.RemoteException, SecurityException, ATMException;

	public Float getBalance(AccountInfo accountInfo)
			throws java.rmi.RemoteException, SecurityException, ATMException;

	public void transferAmount(AccountInfo fromAccountInfo,
			AccountInfo toAccountInfo, float amount)
			throws java.rmi.RemoteException, SecurityException, ATMException;

	public void handleEvent(TransactionNotification transactionInfo)
			throws java.rmi.RemoteException;

	public void notifyListener(TransactionNotification transactionInfo)
			throws java.rmi.RemoteException;

	public void addListener(ATMListener listener)
			throws java.rmi.RemoteException, Exception;
}