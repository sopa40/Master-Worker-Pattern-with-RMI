package mwp;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteFutureMath extends UnicastRemoteObject implements RemoteFuture<Integer>, Serializable{	

	/**
	 * default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1677279911946420391L;
	
	private Integer returnInteger;
	private boolean ready;

	protected RemoteFutureMath() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.returnInteger = null;
		this.ready = false;
	}

	@Override
	public Integer get() throws RemoteException {
		// TODO Auto-generated method stub
		while(!ready) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
		return this.returnInteger;
	}
	
	public void setReturnInteger(Integer returnInteger) {
		this.returnInteger = returnInteger;
		this.ready = true;
	}

}
