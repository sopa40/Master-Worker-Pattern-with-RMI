package mwp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Task 1.2
 * @author Qianli Wang und Nazar Sopiha 
 */
public class WorkerMain extends UnicastRemoteObject implements Worker, Runnable{
	/**
	 * default generated serialVersionUID
	 */
	private static final long serialVersionUID = -3169288224265581859L;
	
	private Master master;
	private boolean running = true;
	
	// Set the number of threads
	public static final int MAX_THREADS = 3;
	
	public WorkerMain(String host, int port) throws RemoteException{
		System.out.println("Worker: Trying to connect to server: " + host + " port: " + port);
		
		try {
			Registry registry = LocateRegistry.getRegistry(host, port);
			master = (Master) registry.lookup("mwp");
			master.registerWorker(this);
		} catch (RemoteException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		String line;
		
		System.out.println("Running in workerMain!");
		System.out.println("Print \"quit\" to quit!");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		
		while(running) {
			try {
				line = bReader.readLine();
				if(line.equals("quit") && line != null) {
					master.unregisterWorker(this);
					running = false;
					bReader.close();
				}
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public <Argument, Result> void start(Task<Argument, Result> t, Pool<Argument> argpool, Pool<Result> respool) throws RemoteException {
        new Thread(new WorkerThread<Argument, Result>(t, argpool, respool)).start();
    }
	
	class WorkerThread<Argument, Result> implements Runnable{
		private Task<Argument, Result> t;
		private Pool<Argument> argpool;
		private Pool<Result> respool;
		
		public WorkerThread(Task<Argument, Result> t, Pool<Argument> argpool, Pool<Result> respool) {
			// TODO Auto-generated constructor stub
			this.t = t;
			this.argpool = argpool;
			this.respool = respool;
		}
		
		@Override
		public void run() {
			try {
				while(running && argpool.size() > 0) {
					Argument argument = argpool.get();
					if(argument != null) {
						Result result = t.exec(argument);
						if(result != null) {
							respool.put(result);
						}
					}
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
