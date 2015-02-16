package cscie160.project;

/* Interface for Security Remote Processing
 * 
 */
public interface SecurityFactory extends java.rmi.Remote {

	public Security getSecurity() throws java.rmi.RemoteException;

}
