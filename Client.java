package cscie160.project;

/**
 * Class to deal with the RMI lookup and perform all the actions against Account
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

public class Client {

	public static void main(String[] args) throws ATMException {
		ATM atm = null;
		try {
			ATMFactory factory = (ATMFactory) Naming
					.lookup("//localhost/atmfactory");
			atm = factory.getATM();
			try {
				ClientInfo client = new ClientInfo();
				atm.addListener(client);
				System.out.println("Client creation successful");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
		testATM(atm);
	}

	public static void testATM(ATM atm) {
		if (atm != null) {
			printBalances(atm);
			performTestOne(atm);
			performTestTwo(atm);
			performTestThree(atm);
			performTestFour(atm);
			performTestFive(atm);
			performTestSix(atm);
			performTestSeven(atm);
			performTestEight(atm);
			performTestNine(atm);
			printBalances(atm);
			performTestTen(atm);
			performTestEleven(atm);
			performTestTwelve(atm);
		}
	}

	public static void printBalances(ATM atm) {
		try {
			System.out.println("Balance(0000001): "
					+ atm.getBalance(getAccountInfo(0000001, 1234)));
			System.out.println("Balance(0000002): "
					+ atm.getBalance(getAccountInfo(0000002, 2345)));
			System.out.println("Balance(0000003): "
					+ atm.getBalance(getAccountInfo(0000003, 3456)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void performTestOne(ATM atm) {
		try {
			atm.getBalance(getAccountInfo(0000001, 5555));
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	public static void performTestTwo(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(0000002, 2345), 500);
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	public static void performTestThree(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(0000001, 1234), 50);
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	public static void performTestFour(ATM atm) {
		try {
			atm.deposit(getAccountInfo(0000001, 1234), 500);
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e);
		}
	}

	public static void performTestFive(ATM atm) {
		try {
			atm.deposit(getAccountInfo(0000002, 2345), 100);
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e);
		}
	}

	public static void performTestSix(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(0000001, 1234), 100);
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e);
		}
	}

	public static void performTestSeven(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(0000003, 3456), 300);
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e);
		}
	}

	public static void performTestEight(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(0000001, 1234), 200);
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	public static void performTestNine(ATM atm) {
		try {
			atm.transferAmount(getAccountInfo(0000001, 1234),
					getAccountInfo(0000002, 2345), 100);
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e);
		}
	}

	public static void performTestTen(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(3, 3456), 1200.00f);
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	public static void performTestEleven(ATM atm) {
		try {
			atm.withdraw(getAccountInfo(3, 3456), -1200.00f);
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	public static void performTestTwelve(ATM atm) {
		try {
			atm.deposit(getAccountInfo(3, 3456), -1200.00f);
		} catch (Exception e) {
			System.out.println("Failed as expected: " + e);
		}
	}

	private static AccountInfo getAccountInfo(int accountNumber, int pinNumber) {
		// TODO Auto-generated method stub
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountNumber(accountNumber);
		accountInfo.setPinNumber(pinNumber);
		return accountInfo;
	}

	public void handleEvent(TransactionNotification transactionNotification)
			throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(transactionNotification.getMessage());
	}

}