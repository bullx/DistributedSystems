import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Broker extends UnicastRemoteObject implements BrokerInterface, Serializable {

	// HashMap<Integer, String> hm = new HashMap<Integer, String>();

	static HashMap<String, ArrayList<Integer>> hmap = new HashMap<String, ArrayList<Integer>>();
	static HashMap<Integer, String> hm = new HashMap<Integer, String>();

	Broker() throws RemoteException {
		super();
	}

	public static void main(String args[]) {
		try {
			BrokerInterface stub = new Broker();

			Naming.rebind("rmi://localhost:5000/broker", stub);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void publish(String id, String update) {
		System.out.println("in publish");

		ArrayList temp = hmap.get(id);
		System.out.println("sent4" + temp.size());
		for (int i = 0; i < temp.size(); i++) {
			String port_object = hm.get(temp.get(i));
			// System.out.println("sent3" + port_object);
			long start = System.currentTimeMillis();
			try {
				Clienthelp stub1 = (Clienthelp) Naming.lookup("rmi://localhost:5000/" + port_object);
				// System.out.println("sentlol");
				stub1.display(update);
				// System.out.println("sent1");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("unable to send");
			}
			// System.out.println("thread exited is" +
			// Thread.currentThread().getName());
			System.out.println("Time taken is" + (System.currentTimeMillis() - start));
			// MultipleServer myRunnable = new MultipleServer(port_object,
			// update,start);
			// Thread t = new Thread(myRunnable);
			// t.start();

		}
		System.out.println("sent");
	}

	public void remove(String m, int id) {
		// hm.remove(id, m);
		// publisher_events.remove(m);

		if (hmap.containsKey(m)) {
			hmap.remove(m);
		}

	}

	public int add(int x, int y) {
		return x + y;
	}

	public String register(String events, int id) throws RemoteException {

		// publisher_events.add(events);
		if (!hmap.containsKey(events)) {
			ArrayList<Integer> new_list = new ArrayList<Integer>();
			// new_list.add(1);
			hmap.put(events, new_list);
			return "Event added ";
		}
		// if (hmap.containsKey(events)) {

		return "Event already present";

		// }

	}

	public String subscribe(int id, String event) {
		System.out.println("subscriber added is " + id);
		String s = null;
		if (hmap.isEmpty()) {
			// ArrayList<Integer> temp = new ArrayList<Integer>();
			// temp.add(id);
			// hmap.put(event, temp);
			s = "No events present to be subscribed";

		} else if (hmap.containsKey(event)) {
			ArrayList<Integer> temp = hmap.get(event);
			temp.add(id);
			hmap.put(event, temp);

			s = "Event subscribed to" + (String) event;
		}
		return s;

	}

	// @Override
	public ArrayList<String> get_events() throws RemoteException {
		Set s = hmap.entrySet();
		Iterator itr = s.iterator();
		ArrayList<String> temp = new ArrayList<String>();
		while (itr.hasNext()) {
			Map.Entry me = (Map.Entry) itr.next();
			// System.out.println("temp size" + temp.size());
			temp.add((String) me.getKey());

		}

		return temp;
	}

	// @Override
	public String remove_subscription(String m, int id) throws RemoteException {
		if (hmap.isEmpty()) {
			return "No events there";

		} else {
			System.out.println("in remove else");
			if (hmap.containsKey(m)) {
				ArrayList<Integer> temp = hmap.get(m);
				if (temp.contains(id)) {
					temp.remove((Integer) id);
					hmap.put(m, temp);
				} else {
					return "event already removed or not subscribed";
				}

				return "Event subscription removed";
			} else {
				return "Event not subscribed to";
			}

		}

	}

	public ArrayList<String> get_subscribed_events(int id) throws RemoteException {
		ArrayList<String> sub_list = new ArrayList<String>();
		if (!hmap.isEmpty()) {
			Set s = hmap.entrySet();
			Iterator itr = s.iterator();
			while (itr.hasNext()) {
				Map.Entry me = (Map.Entry) itr.next();
				String key = (String) me.getKey();
				ArrayList<Integer> temp = hmap.get(key);
				for (int i = 0; i < temp.size(); i++) {
					if (id == temp.get(i)) {
						sub_list.add(key);
					}

				}

			}

		}
		return sub_list;

	}

	// @Override
	public void register_id(int id, String portnumber) throws RemoteException {
		hm.put(id, portnumber);

	}

}