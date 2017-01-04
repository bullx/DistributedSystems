import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public interface Clienthelp  extends Remote {
	public void display(String up)throws RemoteException;

}
