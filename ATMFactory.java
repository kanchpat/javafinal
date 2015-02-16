package cscie160.project;

/* Interface for ATMFactory Remote Processing 
 * @author - Kanchana
 */
public interface ATMFactory extends java.rmi.Remote {

	public ATM getATM() throws java.rmi.RemoteException;
}
