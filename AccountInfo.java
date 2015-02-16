package cscie160.project;

/* This class holds the account number and pin number info to be passed between objects for Validation and bank processing
 * This class is implemented serializable to achieve the remote access usage
 * @author - Kanchana
 */
import java.io.Serializable;

public class AccountInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int accountNumber;
	int pinNumber;

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public AccountInfo() {
		super();
	}

	public AccountInfo(int accountNumber, int pinNumber) {
		this.accountNumber = accountNumber;
		this.pinNumber = pinNumber;
	}

	public AccountInfo getAccountInfo(int accountNumber, int pinNumber) {
		this.accountNumber = accountNumber;
		this.pinNumber = pinNumber;
		return this;
	}
}
