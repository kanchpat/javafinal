/** ATM Implementation is the layer used in Server to deal with accounts. 
 * We support deposit, withdraw and Balance for an Account
 */
package cscie160.project;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.util.LinkedList;
import java.util.ListIterator;

public class ATMImpl extends java.rmi.server.UnicastRemoteObject implements ATM {

	/**
	 * This class would lookup the bank and security Add Bank and Security as
	 * Listeners Notify the listeners when a transaction is received from Client
	 * Check the Access (Authorization and Authentication) through Security
	 * Process the transaction through Transaction Notification based on the
	 * ATMListener Retrieves the listener info and handles it by notifying the
	 * listener
	 */
	private static final long serialVersionUID = 1L;
	float balanceAmountATM = 1000.00f;
	Bank bank = null;
	Security security;
	protected LinkedList<ATMListener> globalListeners = new LinkedList();
	TransactionNotification transactionInfo = null;

	public ATMImpl() throws Exception, java.rmi.RemoteException {
		super();
		lookupBank();
		if (bank == null)
			System.out.println("Bank not yet created");
	}

	public void lookupBank() throws Exception {
		try {
			BankFactory bankFactory = (BankFactory) Naming
					.lookup("//localhost/bankfactory");
			bank = bankFactory.getBank();
			SecurityFactory securityFactory = (SecurityFactory) Naming
					.lookup("//localhost/securityfactory");
			security = securityFactory.getSecurity();
			addListener(bank);
			addListener(security);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}

	public void deposit(AccountInfo accountInfo, float amount)
			throws ATMException, SecurityException, java.rmi.RemoteException {
		if (balanceAmountATM <= amount)
			throw new ATMException("ATM out of order");
		notifyListeners("Deposit");
		transactionInfo = new TransactionNotification(this, accountInfo,
				"Deposit", amount);
		Permission accessLevel = checkAccess(accountInfo, Commands.DEPOSIT);
		if (accessLevel == Permission.PERMITTED) {
			bank.deposit(transactionInfo);
			balanceAmountATM += amount;
		}

	}

	public void withdraw(AccountInfo accountInfo, float amount)
			throws ATMException, SecurityException, java.rmi.RemoteException {
		if (balanceAmountATM <= amount)
			throw new ATMException("ATM out of order");
		notifyListeners("Withdraw");
		transactionInfo = new TransactionNotification(this, accountInfo,
				"Withdraw", amount);

		if (balanceAmountATM == 0)
			throw new ATMException("ATM does not have enough Balance");
		Permission accessLevel = checkAccess(accountInfo, Commands.WITHDRAW);
		if (accessLevel == Permission.PERMITTED) {
			bank.withdraw(transactionInfo);
			balanceAmountATM -= amount;
		}
	}

	public Float getBalance(AccountInfo accountInfo) throws ATMException,
			SecurityException, java.rmi.RemoteException {
		notifyListeners("Get Balance");
		transactionInfo = new TransactionNotification(this, accountInfo,
				"GetBalance");

		Permission accessLevel = checkAccess(accountInfo, Commands.BALANCE);
		if (accessLevel == Permission.PERMITTED) {
			float amount = bank.getBalance(transactionInfo);
			return amount;
		}
		return null;
	}

	public void transferAmount(AccountInfo fromAccountInfo,
			AccountInfo toAccountInfo, float amount) throws ATMException,
			SecurityException, java.rmi.RemoteException {
		notifyListeners("Transfer");
		transactionInfo = new TransactionNotification(this, fromAccountInfo,
				"Transfer", amount);

		Permission accessfromAccount = checkAccess(fromAccountInfo,
				Commands.TRANSFER);
		Permission accesstoAccount = checkAccess(toAccountInfo,
				Commands.TRANSFER);
		if ((accessfromAccount == Permission.PERMITTED)
				&& (accesstoAccount == Permission.PERMITTED)) {
			bank.transferAmount(transactionInfo,
					toAccountInfo.getAccountNumber());
		}
	}

	public Permission checkAccess(AccountInfo accountInfo,
			Commands operationEvent) throws RemoteException, SecurityException {
		Permission accessLevel = security
				.checkAuthenticationDetails(accountInfo);
		if (accessLevel == Permission.PERMITTED) {
			accessLevel = security.checkAuthorizationDetails(accountInfo,
					operationEvent);
			if (accessLevel == Permission.PERMITTED)
				return accessLevel;
			else
				throw new SecurityException(operationEvent
						+ " not allowed for this account");
		}
		throw new SecurityException("Password or Account Info incorrect");
	}

	@Override
	public void addListener(ATMListener listener) throws RemoteException,
			Exception {
		if (!globalListeners.contains(listener)) {
			System.out.println("Adding listener of type "
					+ listener.getClass().getName());
			globalListeners.add(listener);
			System.out.println("globalListeners.size() = "
					+ globalListeners.size());
		}
	}

	/*
	 * This method notifies that the transaction is about to be processed to all
	 * the listeners
	 */
	synchronized void notifyListeners(String event) {
		TransactionNotification transactionNotify = new TransactionNotification(
				this, null, "Transaction waiting to be processed" + event,
				0.00f);
		System.out.println("Distributing events.  Global queue.size() = "
				+ globalListeners.size());
		@SuppressWarnings("unchecked")
		LinkedList<ATMListener> globalListenersClone = (LinkedList<ATMListener>) globalListeners
				.clone();
		// Enumeration enum = globalListenersClone.elements();
		ListIterator<ATMListener> listIterator = globalListenersClone
				.listIterator();
		while (listIterator.hasNext()) {
			ATMListener el = (ATMListener) listIterator.next();
			try {
				System.out.println("Calling handleevent on object of type: "
						+ el.getClass().getName());
				el.handleEvent(transactionNotify);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	/*
	 * This method notifies the Listener that the Transaction Processing is
	 * successful.
	 * 
	 * @see
	 * cscie160.project.ATM#notifyListener(cscie160.project.TransactionNotification
	 * )
	 */
	public void notifyListener(TransactionNotification transactionNotify) {
		@SuppressWarnings("unchecked")
		LinkedList<ATMListener> globalListenersClone = (LinkedList<ATMListener>) globalListeners
				.clone();
		// Enumeration enum = globalListenersClone.elements();
		ListIterator<ATMListener> listIterator = globalListenersClone
				.listIterator();
		while (listIterator.hasNext()) {
			ATMListener el = (ATMListener) listIterator.next();
			try {
				el.handleEvent(transactionNotify);
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
	}

	/*
	 * This method prints the processing message to the Console of ATMServer
	 * 
	 * @see
	 * cscie160.project.ATM#handleEvent(cscie160.project.TransactionNotification
	 * )
	 */
	public void handleEvent(TransactionNotification transactionInfo)
			throws RemoteException {
		System.out.println(transactionInfo.getMessage());
	}

}
