/** Class to bind the RMI registry in the server 
 * 
 */
package cscie160.project;

import java.rmi.Naming;

public class BankFactoryImpl extends java.rmi.server.UnicastRemoteObject
		implements BankFactory {
	private static final long serialVersionUID = 1L;

	public BankFactoryImpl() throws Exception, java.rmi.RemoteException {
		super();
	}

	public void bindBank() throws Exception {
		BankFactoryImpl bankFactoryImpl = new BankFactoryImpl();
		try {
			Naming.rebind("//localhost/bankfactory", bankFactoryImpl);
			System.out.println("Successful bind");
		} catch (Exception rmiEx) {
			System.out.println("No registry found, creating our own.");
			Naming.rebind("//localhost/bankfactory", bankFactoryImpl);
			System.out.println("Successful bind");
		}
	}

	public Bank getBank() throws java.rmi.RemoteException {
		try {
			BankImpl bank = new BankImpl();
			return bank;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
