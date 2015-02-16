package cscie160.project;

import java.rmi.RemoteException;
import java.util.HashMap;

/* This class loads the Authentication and Authorization Info currently available
 * It verifies and validates against the existing processing
 * It also handles the information back to the source 
 */

public class SecurityImpl extends java.rmi.server.UnicastRemoteObject implements
		Security {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Authorization authorization;

	HashMap<Integer, Integer> pinInfo = new HashMap<Integer, Integer>();
	HashMap<Integer, Authorization> authInfo = new HashMap<Integer, Authorization>();

	public SecurityImpl() throws Exception, java.rmi.RemoteException {
		if (pinInfo.isEmpty() == true)
			assignAuthenticationDetails();
		if (authInfo.isEmpty() == true)
			assignAuthorizationDetails();
	}

	private void assignAuthenticationDetails() {
		pinInfo.put((Integer) 1, (Integer) 1234);
		pinInfo.put((Integer) 2, (Integer) 2345);
		pinInfo.put((Integer) 3, (Integer) 3456);
	}

	private void assignAuthorizationDetails() {
		authorization = new Authorization(Permission.PERMITTED,
				Permission.PERMITTED, Permission.PERMITTED);
		authInfo.put(1, authorization);
		authorization = new Authorization(Permission.PERMITTED,
				Permission.NOTPERMITTED, Permission.PERMITTED);
		authInfo.put(2, authorization);
		authorization = new Authorization(Permission.NOTPERMITTED,
				Permission.PERMITTED, Permission.PERMITTED);
		authInfo.put(3, authorization);
	}

	public void changeAuthorizationDetails(int accountNumber, int pinNo) {
		pinInfo.put((Integer) accountNumber, (Integer) pinNo);
	}

	public void changeAuthenticationDetails(int accountNumber,
			Commands operationEvent, Permission permissionInfo)
			throws SecurityException {
		authorization = authInfo.get((Integer) accountNumber);
		if (operationEvent == Commands.DEPOSIT) {
			authorization.setDepositsValue(permissionInfo);
		} else if (operationEvent == Commands.WITHDRAW) {
			authorization.setWithdrawValue(permissionInfo);
		} else if (operationEvent == Commands.BALANCE) {
			authorization.setBalanceValue(permissionInfo);
		} else
			throw new SecurityException("Unknown Command");

		authInfo.put((Integer) accountNumber, authorization);

	}

	public void addAuthorizationDetails(int accountNumber, int pinNo) {
		pinInfo.put((Integer) accountNumber, (Integer) pinNo);
	}

	public void addAuthenticationDetails(int accountNumber,
			Commands operationEvent, Permission permissionInfo)
			throws SecurityException {
		if (operationEvent == Commands.DEPOSIT) {
			authorization.setDepositsValue(permissionInfo);
			authorization.setBalanceValue(Permission.NOTPERMITTED);
			authorization.setWithdrawValue(Permission.NOTPERMITTED);
			authorization.setTransferValue(Permission.NOTPERMITTED);
		} else if (operationEvent == Commands.WITHDRAW) {
			authorization.setWithdrawValue(permissionInfo);
			authorization.setBalanceValue(Permission.NOTPERMITTED);
			authorization.setDepositsValue(Permission.NOTPERMITTED);
			authorization.setTransferValue(Permission.NOTPERMITTED);
		} else if (operationEvent == Commands.BALANCE) {
			authorization.setWithdrawValue(Permission.NOTPERMITTED);
			authorization.setDepositsValue(Permission.NOTPERMITTED);
			authorization.setBalanceValue(permissionInfo);
			authorization.setTransferValue(Permission.NOTPERMITTED);
		} else if (operationEvent == Commands.TRANSFER) {
			authorization.setWithdrawValue(Permission.NOTPERMITTED);
			authorization.setDepositsValue(Permission.NOTPERMITTED);
			authorization.setBalanceValue(Permission.NOTPERMITTED);
			authorization.setTransferValue(permissionInfo);
		} else
			throw new SecurityException("Unknown Command");

		authInfo.put((Integer) accountNumber, authorization);

	}

	public Permission checkAuthenticationDetails(AccountInfo accountInfo) {

		int pinNumber = pinInfo.get((Integer) accountInfo.getAccountNumber());
		if (pinNumber == accountInfo.getPinNumber())
			return Permission.PERMITTED;
		else
			return Permission.NOTPERMITTED;
	}

	public Permission checkAuthorizationDetails(AccountInfo accountInfo,
			Commands operationEvent) throws SecurityException {
		authorization = authInfo.get((Integer) accountInfo.getAccountNumber());
		if (operationEvent == Commands.DEPOSIT) {
			return authorization.getDepositsValue();
		} else if (operationEvent == Commands.WITHDRAW) {
			return authorization.getWithdrawValue();
		} else if (operationEvent == Commands.BALANCE) {
			return authorization.getBalanceValue();
		} else if (operationEvent == Commands.TRANSFER) {
			return authorization.getTransferValue();
		} else
			throw new SecurityException("Unknown Command");
	}

	private class Authorization {
		private Permission depositsValue;
		private Permission withdrawValue;
		private Permission balanceValue;
		private Permission transferValue;

		Authorization(Permission depositsValue, Permission withdrawValue,
				Permission balanceValue) {
			this.depositsValue = depositsValue;
			this.withdrawValue = withdrawValue;
			this.balanceValue = balanceValue;
			this.transferValue = Permission.PERMITTED;
		}

		public Permission getDepositsValue() {
			return depositsValue;
		}

		public void setDepositsValue(Permission depositsValue) {
			this.depositsValue = depositsValue;
		}

		public Permission getTransferValue() {
			return transferValue;
		}

		public void setTransferValue(Permission transferValue) {
			this.transferValue = transferValue;
		}

		public Permission getWithdrawValue() {
			return withdrawValue;
		}

		public void setWithdrawValue(Permission withdrawValue) {
			this.withdrawValue = withdrawValue;
		}

		public Permission getBalanceValue() {
			return balanceValue;
		}

		public void setBalanceValue(Permission balanceValue) {
			this.balanceValue = balanceValue;
		}

	}

	@Override
	public void handleEvent(TransactionNotification transactionInfo)
			throws RemoteException {
		// TODO Auto-generated method stub
		Object object = transactionInfo.getSource();
		if (object instanceof ATM) {
			ATM atm = (ATM) object;
			TransactionNotification transaction = new TransactionNotification(
					this, transactionInfo.getAccountInfo(),
					"Operation Performed from Security");
			atm.handleEvent(transaction);
		}

	}

}