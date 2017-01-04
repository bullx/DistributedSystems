import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class MyServer {

	public static void main(String args[]) {
		int id = 2;

		BrokerInterface stub = null;
		try {
			stub = (BrokerInterface) Naming.lookup("rmi://localhost:5000/broker");

			// String m = "Ind 119/0";
			// stub.publish("cricket", m);
		} catch (Exception e) {
			System.out.println("Error occured");
		}

		while (true) {

			System.out.println("1 to register events");
			System.out.println("2 to publish");
			System.out.println("3 to remove events");
			Scanner s = new Scanner(System.in);
			int option = s.nextInt();

			if (option == 1) {

				try {
					String reply = stub.register("football", id);
					System.out.println(reply);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			} else if (option == 2) {
				try {
					String m = "Manu 1-0 Chelsea";
					stub.publish("football", m);
					System.out.println("published event");
				} catch (Exception e) {
					System.out.println("Error occured");
				}
			} else if (option == 3) {
				try {
					stub.remove("football", id);
					System.out.println("removed event");
				} catch (Exception e) {
					System.out.println("Error occured");
				}
			}

		}

	}

}