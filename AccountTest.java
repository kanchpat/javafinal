/** This is a junitTest for Account.java. Test is performed for getters and setters
 * 
 */
package cscie160.project;

import junit.framework.TestCase;

public class AccountTest extends TestCase {

	private Account account;

	public AccountTest(String name) {
		super(name);
	}

	public void setUp() {
		this.account = new Account();
	}

	public void tearDown() {
		this.account = null;
	}

	public final void testGetAmount() {
		float amount = 3.0f;
		Account account = new Account(amount);
		assertTrue(
				"getAmount Broken,expected" + amount + "got"
						+ account.getAmount(), amount == account.getAmount());
	}

	public final void testSetAmount() {
		Account account = new Account();
		float amount = 3.0f;
		account.setAmount(amount);
		assertTrue(
				"setAmount Broken,expected" + amount + "set"
						+ account.getAmount(), amount == account.getAmount());
	}

	public static void main(String argv[]) {
		junit.textui.TestRunner.run(AccountTest.class);
	}

}
