package cscie160.project;

import java.rmi.RemoteException;

import junit.framework.TestCase;
import org.junit.Test;

public class ATMImplTest extends TestCase {

	public ATMImplTest(String name) {
		super(name);
	}

	@Test
	public final void testDeposit() throws RemoteException, Exception {
		ATMImpl atm = new ATMImpl();
		AccountInfo accountInfo = new AccountInfo(1, 1234);
		try {
			atm.deposit(accountInfo, -100.00f);
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("testDeposit Broken" + atm.toString(), true);
		}
		float amount = 3.2f;
		try {
			atm.deposit(accountInfo, amount);
			assertTrue(
					"testDeposit expected " + amount + "got "
							+ atm.getBalance(accountInfo),
					amount == atm.getBalance(accountInfo));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		atm = null;
	}

	@Test
	public final void testWithdraw() throws RemoteException, Exception {
		ATMImpl atm = new ATMImpl();
		AccountInfo accountInfo = new AccountInfo(3, 3456);
		try {
			atm.withdraw(accountInfo, 600.00f);
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("testDeposit Broken" + atm.toString(), true);
		}
		float amount = 500.00f;
		try {
			atm.withdraw(accountInfo, amount);
			assertTrue(
					"testDeposit expected " + amount + "got "
							+ atm.getBalance(accountInfo),
					amount == atm.getBalance(accountInfo));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		amount = 1200.00f;
		try {
			atm.withdraw(accountInfo, amount);
		} catch (ATMException e) {
			// TODO Auto-generated catch block
			assertTrue("test Withdraw Broken " + amount + "got exception", true);
		}

		atm = null;

	}

	@Test
	public final void testGetBalance() throws RemoteException, Exception {
		ATMImpl atm = new ATMImpl();
		AccountInfo accountInfo = new AccountInfo(3, 3456);
		try {
			atm.getBalance(accountInfo);
			assertTrue("testBalance Expected" + atm.toString(),
					600.00f == atm.getBalance(accountInfo));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
		}
		atm = null;
	}

	@Test
	public final void testTransferAmount() throws RemoteException, Exception {
		ATMImpl atm = new ATMImpl();
		AccountInfo fromAccountInfo = new AccountInfo(3, 3456);
		AccountInfo toAccountInfo = new AccountInfo(2, 2345);

		try {
			float amount = atm.getBalance(toAccountInfo);
			atm.transferAmount(fromAccountInfo, toAccountInfo, 100.00f);
			assertTrue("testTransfer Expected" + atm.toString(),
					(amount + 100.00f) == atm.getBalance(toAccountInfo));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
		}
		atm = null;
	}

	@Test
	public final void testCheckAccess() throws Exception {
		ATMImpl atm = new ATMImpl();
		AccountInfo accountInfo = new AccountInfo(2, 2345);
		try {
			float amount = atm.getBalance(accountInfo);
			atm.deposit(accountInfo, 100.00f);
			assertTrue("testDeposit Expected" + atm.toString(),
					(amount + 100.00f) == atm.getBalance(accountInfo));
		} catch (ATMException e) {
			// TODO Auto-generated catch block
		}
		try {
			atm.withdraw(accountInfo, 100.00f);
		} catch (SecurityException e) {
			assertTrue("testWithdraw Expected Exception" + atm.toString(), true);
		}
		atm = null;

	}

	public static void main(String argv[]) {
		junit.textui.TestRunner.run(ATMImplTest.class);
	}

}
