package cscie160.project;

import java.rmi.RemoteException;

import junit.framework.TestCase;
import org.junit.Test;

public class SecurityImplTest extends TestCase {

	public SecurityImplTest(String name) {
		super(name);
	}

	@Test
	public final void testCheckAuthenticationDetails() throws RemoteException,
			Exception {
		SecurityImpl security = new SecurityImpl();
		AccountInfo accountInfo = new AccountInfo(1, 3456);
		Permission permission = security
				.checkAuthenticationDetails(accountInfo);
		assertTrue("testDeposit Broken" + security.toString(),
				permission == Permission.NOTPERMITTED);

		accountInfo = new AccountInfo(1, 1234);
		permission = security.checkAuthenticationDetails(accountInfo);
		assertTrue("testauthentication expected " + Permission.PERMITTED
				+ "got " + permission, permission == Permission.PERMITTED);
		security = null;
	}

	@Test
	public final void testCheckAuthorizationDetails() throws RemoteException,
			Exception {
		SecurityImpl security = new SecurityImpl();
		AccountInfo accountInfo = new AccountInfo(1, 1234);
		Permission permission = security.checkAuthorizationDetails(accountInfo,
				Commands.DEPOSIT);
		assertTrue("testDeposit Broken" + security.toString(),
				permission == Permission.PERMITTED);

		accountInfo = new AccountInfo(2, 2345);
		permission = security.checkAuthorizationDetails(accountInfo,
				Commands.WITHDRAW);
		assertTrue("testauthentication expected " + Permission.PERMITTED
				+ "got " + permission, permission == Permission.NOTPERMITTED);

		accountInfo = new AccountInfo(1, 1234);
		permission = security.checkAuthorizationDetails(accountInfo,
				Commands.TRANSFER);
		assertTrue("testauthentication expected " + Permission.PERMITTED
				+ "got " + permission, permission == Permission.PERMITTED);

		accountInfo = new AccountInfo(1, 1234);
		permission = security.checkAuthorizationDetails(accountInfo,
				Commands.BALANCE);
		assertTrue("testauthentication expected " + Permission.PERMITTED
				+ "got " + permission, permission == Permission.PERMITTED);

		security = null;
	}

	@Test
	public static void main(String argv[]) {
		junit.textui.TestRunner.run(SecurityImplTest.class);
	}

}
