/** Class to bind the RMI registry in the server 
 * 
 */
package cscie160.project;

import java.rmi.Naming;

public class ATMFactoryImpl extends java.rmi.server.UnicastRemoteObject
		implements ATMFactory {
	private static final long serialVersionUID = 1L;

	public ATMFactoryImpl() throws Exception, java.rmi.RemoteException {
		super();
	}

	public void bindATM() throws Exception {
		ATMFactoryImpl atmFactoryImpl = new ATMFactoryImpl();
		try {
			Naming.rebind("//localhost/atmfactory", atmFactoryImpl);
			System.out.println("Successful bind");
		} catch (Exception rmiEx) {
			System.out.println("No registry found, creating our own.");
			Naming.rebind("//localhost/atmfactory", atmFactoryImpl);
			System.out.println("Successful bind");
		}
	}

	public ATM getATM() throws java.rmi.RemoteException {
		try {
			ATM ATM = new ATMImpl();
			return ATM;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
