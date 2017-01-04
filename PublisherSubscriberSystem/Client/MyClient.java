import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class MyClient extends UnicastRemoteObject implements Clienthelp {
	static int id = 1;
	static String port = "";
	static final int len = 10;
	static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	
	ArrayList<String> my_events = new ArrayList<String>();
	
	protected MyClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		Random rand = new Random();
		id= rand.nextInt(100000);
		// Adder stub = null;
		BrokerInterface stub2 = null;
		Clienthelp stub1 = null;
		try {
		
			stub1 = new MyClient();
			StringBuffer ss = new StringBuffer();
	        for(int i=0; i<len; i++){
	        	Random rand1 = new Random();
	            char ch = letters.charAt(rand1.nextInt(letters.length()));
	            ss.append(ch);
	        }
	        port= ss.toString();
			Naming.rebind("rmi://localhost:5000/" + port, stub1);

			System.out.println("Client accepting");
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			stub2 = (BrokerInterface) Naming.lookup("rmi://localhost:5000/broker");
			// System.out.println(stub2.add(34, 4));
			// String event = "cricket";
			// stub.subscribe(id, event);
			stub2.register_id(id, port);
		} catch (Exception e) {
		}

		while (true) {
			System.out.println("1 to get events");
			System.out.println("2 to subscribe");
			System.out.println("3 to unsubscribe");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();

			if (option == 1) {

				try {
					ArrayList<String> temp = stub2.get_events();
					if (temp.isEmpty()) {
						System.out.println("No events present");
					} else {
						System.out.println(temp.size());
						for (int i = 0; i < temp.size(); i++) {
							System.out.println("Events are " + temp.get(i));
						}

					}

				} catch (RemoteException e) {
					e.printStackTrace();
				}

			} else if (option == 2) {
				try {
					System.out.println("Enter event name");
					String sin = s.next();
					String sub = stub2.subscribe(id, sin);
					System.out.println("subscribed to event " + sub);
				} catch (Exception e) {
					System.out.println("Error occured");
				}
			} else if (option == 3) {
				try {
					// ArrayList<String> temp = stub2.get_subscribed_events(id);
					// System.out.println("Events are :");
					// for (int i = 0; i < temp.size(); i++) {
					// System.out.println(temp.get(i));
					// }
					// String to_be_removed = s.next();
					System.out.println("removed event is " + stub2.remove_subscription("cricket", id));
				} catch (Exception e) {
					System.out.println("Error occured");
					e.printStackTrace();
				}
			}
		}
		// System.out.println("after main");
	}

	public void display(String up) {
		System.out.println("Score is : " + up);

	}
}