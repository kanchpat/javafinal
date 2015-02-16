package cscie160.project;

/* Interface for Bank Factory Remote Processing*/

public interface BankFactory extends java.rmi.Remote {

	public Bank getBank() throws java.rmi.RemoteException;
}
