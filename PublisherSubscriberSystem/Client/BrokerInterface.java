import java.rmi.*;
import java.util.ArrayList;
import java.util.HashMap;

public interface BrokerInterface extends Remote {
	public int add(int x, int y) throws RemoteException;

	public String subscribe(int id,String event)throws RemoteException;

	//public void remove();

	public void publish(String id,String update)throws RemoteException;

	public void remove(String m,int id)throws RemoteException;
	
	public String register(String e,int id)throws RemoteException;
	
	public ArrayList<String> get_events()throws RemoteException;
	
	public String remove_subscription(String m,int id)throws RemoteException;
	
	public ArrayList<String> get_subscribed_events(int id)throws RemoteException;
	
	public void register_id(int id,String portnumber)throws RemoteException;
	
}