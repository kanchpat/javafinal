package cscie160.project;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class SecurityFactoryImpl extends java.rmi.server.UnicastRemoteObject
		implements SecurityFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SecurityFactoryImpl() throws RemoteException {
		super();
	}

	public void bindSecurity() throws Exception {
		SecurityFactoryImpl securityFactory = new SecurityFactoryImpl();
		try {
			Naming.rebind("//localhost/securityfactory", securityFactory);
			System.out.println("Successful bind");
		} catch (Exception rmiEx) {
			System.out.println("No registry found, creating our own.");
			Naming.rebind("//localhost/securityfactory", securityFactory);
			System.out.println("Successful bind");
		}
	}

	@Override
	public Security getSecurity() throws RemoteException {
		try {
			SecurityImpl security = new SecurityImpl();
			return security;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
