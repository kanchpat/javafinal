package cscie160.project;

import java.rmi.Remote;

public class TransactionNotification extends java.util.EventObject {
	/**
	 * This class contains the Event object which is transferred with all
	 * instances of Processing
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message = null;
	private java.rmi.Remote remoteObject = null;
	private AccountInfo accountInfo;
	float amount;

	TransactionNotification(Object object, AccountInfo accountInfo,
			String message, float amount) {
		super(object);
		this.message = message;
		this.accountInfo = accountInfo;
		this.amount = amount;
		// If we were given a Remote object we will keep
		// a separate reference to it, which will be serialized
		// along with the object when it is sent over the wire
		if (object instanceof Remote)
			this.remoteObject = (Remote) object;
	}

	TransactionNotification(Object object, AccountInfo accountInfo,
			String message) {
		super(object);
		this.message = message;
		this.accountInfo = accountInfo;
		this.amount = 0.00f;
		// If we were given a Remote object we will keep
		// a separate reference to it, which will be serialized
		// along with the object when it is sent over the wire
		if (object instanceof Remote)
			this.remoteObject = (Remote) object;
	}

	public Float getAmount() {
		return amount;
	}

	public AccountInfo getAccountInfo() {
		return accountInfo;
	}

	public String getMessage() {
		return message;
	}

	// Overloading the parent getSource to return the Remote object
	// if there is one
	public Object getSource() {
		if (remoteObject != null)
			return remoteObject;
		else
			return super.getSource();
	}

	public String toString() {
		if (accountInfo == null) {
			return message;
		}
		if (message.equalsIgnoreCase("GetBalance"))
			return message + " performed on Account "
					+ getAccountInfo().getAccountNumber();

		return message + " performed on Account "
				+ getAccountInfo().getAccountNumber() + ":" + amount;
	}
}
