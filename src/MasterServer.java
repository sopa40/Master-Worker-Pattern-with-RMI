package mwp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.InetAddress;
import java.net.UnknownHostException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

/**
 * Task 1.3.2 + 1.3.3
 * @author Qianli Wang und Nazar Sopiha
 *
 */

public class MasterServer extends UnicastRemoteObject implements Master, Server{

	/**
	 * default generated serialVersionUID 		
	 */
	private static final long serialVersionUID = -5143971522032340265L;
	
	private static ArrayList<Worker> workers;
	private Registry registry;
	private String lineString;
	protected static final String NAME_STRING = "mwp";

	protected MasterServer(int port) throws RemoteException {
		// TODO Auto-generated constructor stub
		
		System.out.println("MasterServer inits!");
		System.out.println("***Print 'q' to quit and 's' to check out the number of workers!***");
		registry = LocateRegistry.createRegistry(port);
		registry.rebind(NAME_STRING, this);
		
		workers = new ArrayList<Worker>();        
		
		try {
			String addressString = (InetAddress.getLocalHost()).toString();
			System.out.println("MasterServer is running on " + addressString + ", port: " + port);
		} catch (UnknownHostException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		running();
	}
	
	private void running() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				lineString = br.readLine();
				if (lineString != null) {
					if (lineString.equals("q")) {
						System.out.println("Bye!");
						break;
					} else if (lineString.equals("s")) {
						System.out.println("There are " + workers.size() + " workers!");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(-1);
	}

	@Override
	public <Argument, Result, ReturnObject> RemoteFuture<ReturnObject> doJob(Job<Argument, Result, ReturnObject> j)
			throws RemoteException {
		// TODO Auto-generated method stub

		// Create 2 pools 
		PoolMain<Argument> argPool = new PoolMain<Argument>();
		PoolMain<Result> resPool = new PoolMain<Result>();
		
		// Job splitted
		j.split(argPool, workers.size());
		System.out.println("doJob() is invoked with " + workers.size() + " workers!");
		
		// Worker starts
		for(Worker w: workers) {
			w.start(j.getTask(), argPool, resPool);
		}
		System.out.println("Work distributed!");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					while(argPool.size() > 0) {
						Thread.sleep(1);
					}
					System.out.println("Server: Merging...");
					j.merge(resPool);
				} catch (InterruptedException | RemoteException e) {
					// TODO: handle exception
				}
			}
		}).start();
		
		// Return future object
		return j.getFuture();
	}

	@Override
	public void registerWorker(Worker w) throws RemoteException {
		// TODO Auto-generated method stub
		workers.add(w);
		System.out.println("Server: Worker" + w + " is registered!");
		System.out.println("Server: Number of workers: " + workers.size());
	}

	@Override
	public void unregisterWorker(Worker w) throws RemoteException {
		// TODO Auto-generated method stub
		workers.remove(w);
		System.out.println("Server: Worker" + w + " is unregistered!");
		System.out.println("Server: Number of workers: " + workers.size());
	}
}
