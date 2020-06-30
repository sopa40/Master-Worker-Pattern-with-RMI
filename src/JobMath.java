package mwp;

import java.rmi.RemoteException;

public class JobMath implements Job<Integer, Integer, Integer>{

	/**
	 * default generated serialVersionUID
	 */
	private static final long serialVersionUID = 4717413864889496183L;
	
	private TaskMath task;
	private RemoteFutureMath remoteFuture;
	private Integer[] numbers;
	
	public JobMath(Integer[] numbers) throws RemoteException {
		// TODO Auto-generated constructor stub
		//this.remoteFuture = new RemoteFutureMath();
		this.task = new TaskMath();
		this.numbers = numbers;
	}

	@Override
	public Task<Integer, Integer> getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}

	@Override
	public RemoteFutureMath getFuture() {
		// TODO Auto-generated method stub
		if(this.remoteFuture == null) {
			try {
				this.remoteFuture = new RemoteFutureMath();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.remoteFuture;
	}

	@Override
	public void split(Pool<Integer> argPool, int workerCount) {
		// TODO Auto-generated method stub
		for(Integer number: numbers) {
			try {
				argPool.put(number);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void merge(Pool<Integer> resPool) {
		// TODO Auto-generated method stub
		Integer tempInteger = 0;
		Integer i;
		try {
			while((i = resPool.size()) != null) {
				tempInteger += i;
			}
			this.getFuture().setReturnInteger(tempInteger);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block   
			e.printStackTrace();
		}
	}

}
