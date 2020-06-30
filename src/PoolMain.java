package mwp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Task 1.3.1
 * @author Qianli Wang und Nazar Sopiha
 *
 * @param <T>
 */

public class PoolMain<T> extends UnicastRemoteObject implements Pool<T> {
	/**
	 * default generated serialVersionUID
	 */
	private static final long serialVersionUID = -9093661638262345380L;
	
	private volatile Queue<T> queue;
	
	public PoolMain() throws RemoteException{
		// TODO Auto-generated constructor stub
		queue = new LinkedList<T>();
	}
	
	/**
	 * Use of lock-synchronization to keep consistency 
	 */
	@Override
	public synchronized void put(T t) throws RemoteException {
		// TODO Auto-generated method stub
		queue.offer(t);
	}

	@Override
	public synchronized T get() throws RemoteException {
		// TODO Auto-generated method stub
		return queue.remove();
	}

	@Override
	public synchronized int size() throws RemoteException {
		// TODO Auto-generated method stub
		return queue.size();
	}

}
