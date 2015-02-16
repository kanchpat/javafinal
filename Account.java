package cscie160.project;

/* This class maintains the account information. Currently it holds just amount field 
 * @author - Kanchana 
 * */

public class Account {
	private float amount;

	public Account() {
		amount = 0;
	}

	public Account(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
}
