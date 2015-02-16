package cscie160.project;

import java.rmi.RemoteException;

import junit.framework.TestCase;
import org.junit.Test;

public class BankImplTest extends TestCase {

	public BankImplTest(String name) {
		super(name);
	}

	@Test
	public final void testDeposit() throws RemoteException, Exception {
		BankImpl bank = new BankImpl();
		AccountInfo accountInfo = new AccountInfo(1, 1234);
		float amount = -100.00f;
		TransactionNotification transNotify = new TransactionNotification(this,
				accountInfo, "Deposit", amount);

		try {
			bank.deposit(transNotify);
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("testDeposit Broken" + bank.toString(), true);
		}
		amount = 100.00f;
		try {
			transNotify = new TransactionNotification(this, accountInfo,
					"Deposit", amount);
			bank.deposit(transNotify);
			assertTrue(
					"testDeposit expected " + amount + "got "
							+ bank.getBalance(transNotify),
					amount == bank.getBalance(transNotify));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bank = null;
	}

	@Test
	public final void testWithdraw() throws RemoteException, Exception {
		BankImpl bank = new BankImpl();
		AccountInfo accountInfo = new AccountInfo(1, 1234);
		float amount = 100.00f;
		TransactionNotification transNotify = new TransactionNotification(this,
				accountInfo, "Withdraw", amount);

		try {
			bank.withdraw(transNotify);
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("testWithdrawn Broken" + bank.toString(), true);
		}
		amount = 600.00f;
		accountInfo = new AccountInfo(3, 3456);
		try {
			transNotify = new TransactionNotification(this, accountInfo,
					"Deposit", amount);
			transNotify = new TransactionNotification(this, accountInfo,
					"Withdraw", amount);
			bank.withdraw(transNotify);
			amount = 0.00f;
			assertTrue(
					"testWithdrawn expected " + amount + "got "
							+ bank.getBalance(transNotify),
					amount == bank.getBalance(transNotify));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bank = null;

	}

	@Test
	public final void testGetBalance() throws RemoteException, Exception {
		BankImpl bank = new BankImpl();
		AccountInfo accountInfo = new AccountInfo(1, 1234);
		float amount = 100.00f;
		TransactionNotification transNotify = new TransactionNotification(this,
				accountInfo, "Balance", amount);

		try {
			bank.getBalance(transNotify);
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("testBalance Broken" + bank.toString(), true);
		}
		bank = null;

	}

	@Test
	public final void testTransferAmount() throws RemoteException, Exception {
		BankImpl bank = new BankImpl();
		AccountInfo fromAccountInfo = new AccountInfo(1, 1234);
		AccountInfo toAccountInfo = new AccountInfo(2, 2345);
		float amount = 100.00f;
		TransactionNotification transNotify = new TransactionNotification(this,
				fromAccountInfo, "Transfer", amount);

		try {
			bank.transferAmount(transNotify, toAccountInfo.getAccountNumber());
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("test Transfer Broken" + bank.toString(), true);
		}
		bank = null;
	}

	@Test
	public static void main(String argv[]) {
		junit.textui.TestRunner.run(BankImplTest.class);
	}

}
