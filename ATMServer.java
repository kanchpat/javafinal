package cscie160.project;

/**
 * Instantiate the RMI and bind the rmi.
 */
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;

public class ATMServer {
	public static void main(String[] args) throws Exception {
		try {
			ATMFactoryImpl factory = new ATMFactoryImpl();
			factory.bindATM();
			System.out.println("DONE");
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
}
