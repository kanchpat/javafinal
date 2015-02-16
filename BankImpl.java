/** ATM Implementation is the layer used in Server to deal with accounts. 
 * We support deposit, withdraw and Balance for an Account
 */
package cscie160.project;

import java.rmi.RemoteException;
import java.util.HashMap;

public class BankImpl extends java.rmi.server.UnicastRemoteObject implements
		Bank {

	/**
	 * This class creates and loads the account details currently It also
	 * processes the transaction received with Transaction Notification After
	 * processing this calls the handle processing as appropriate
	 */
	private static final long serialVersionUID = 1L;
	HashMap<Integer, Account> accountMap = new HashMap<Integer, Account>();
	Account account = new Account();

	public BankImpl() throws Exception, java.rmi.RemoteException {
		super();
		loadAccountDetails();
	}

	public void loadAccountDetails() {
		account.setAmount(0.00f);
		accountMap.put((Integer) 1, account);

		account = new Account();
		account.setAmount(100.00f);
		accountMap.put((Integer) 2, account);

		account = new Account();
		account.setAmount(600.00f);
		accountMap.put((Integer) 3, account);
	}

	public void deposit(TransactionNotification transactionInfo)
			throws ATMException, java.rmi.RemoteException {
		// TODO Auto-generated method stub
		int accountNumber = transactionInfo.getAccountInfo().getAccountNumber();
		float balance;
		if (transactionInfo.getAmount() < 0)
			throw new ATMException("Amount specified less than allowed");

		account = accountMap.get((Integer) accountNumber);
		balance = account.getAmount();
		balance += transactionInfo.getAmount();
		account.setAmount(balance);
		accountMap.put(accountNumber, account);
		handleEvent(transactionInfo);
	}

	public void deposit(int accountNumber, float amount) throws ATMException,
			java.rmi.RemoteException {
		// TODO Auto-generated method stub
		float balance;
		if (amount < 0)
			throw new ATMException("Amount specified less than allowed");

		account = accountMap.get((Integer) accountNumber);
		balance = account.getAmount();
		balance += amount;
		account.setAmount(balance);
		accountMap.put(accountNumber, account);
	}

	public void withdraw(TransactionNotification transactionInfo)
			throws ATMException, java.rmi.RemoteException {
		// TODO Auto-generated method stub
		int accountNumber = transactionInfo.getAccountInfo().getAccountNumber();
		float amount = transactionInfo.getAmount();

		float balance;

		if (amount <= 0)
			throw new ATMException("Amount value specified 0");

		account = accountMap.get((Integer) accountNumber);
		balance = account.getAmount();

		if (balance >= amount) {
			balance -= amount;
			account.setAmount(balance);
		} else {
			throw new ATMException("Amount specified more than the balance");
		}
		handleEvent(transactionInfo);
	}

	public void withdraw(int accountNumber, float amount) throws ATMException,
			java.rmi.RemoteException {
		// TODO Auto-generated method stub
		float balance;
		if (amount <= 0)
			throw new ATMException("Amount value specified 0");
		account = accountMap.get((Integer) accountNumber);
		balance = account.getAmount();
		if (balance >= amount) {
			balance -= amount;
			account.setAmount(balance);
		} else {
			throw new ATMException("Amount specified more than the balance");
		}
	}

	public Float getBalance(TransactionNotification transactionInfo)
			throws ATMException, java.rmi.RemoteException {
		// TODO Auto-generated method stub
		int accountNumber = transactionInfo.getAccountInfo().getAccountNumber();
		account = accountMap.get((Integer) accountNumber);
		float balance = account.getAmount();
		handleEvent(transactionInfo);
		return balance;
	}

	public void transferAmount(TransactionNotification transactionInfo,
			int toAccount) throws ATMException, RemoteException {
		// TODO Auto-generated method stub
		int fromaccountNumber = transactionInfo.getAccountInfo()
				.getAccountNumber();
		float amount = transactionInfo.getAmount();
		deposit(toAccount, amount);
		withdraw(fromaccountNumber, amount);
		handleEvent(transactionInfo);
	}

	/*
	 * It calls the appropriate event handlers for their processing
	 * 
	 * @see
	 * cscie160.project.Bank#handleEvent(cscie160.project.TransactionNotification
	 * )
	 */
	public void handleEvent(TransactionNotification transactionInfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		Object object = transactionInfo.getSource();
		TransactionNotification transaction = null;
		if (transactionInfo.getAccountInfo() != null) {
			if (object instanceof ATM) {
				ATM atm = (ATM) object;
				transaction = new TransactionNotification(this,
						transactionInfo.getAccountInfo(),
						transactionInfo.getMessage(),
						transactionInfo.getAmount());
				atm.notifyListener(transaction);
			}
		} else {
			System.out.println(transactionInfo.getMessage());
		}
	}
}
