package mwp;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class Main {

	private static final String defaultHost = "127.0.0.1";
    private static final int defaultPort = 6666;

    public static void main(String[] args) {
        try {
            if (args[0].equals("Server")) {
                new MasterServer(defaultPort);
            } else if (args[0].equals("Client")) {
            	new ClientMath(defaultHost, defaultPort).execute();
            } else if (args[0].equals("Worker")) {
                new WorkerMain(defaultHost, defaultPort).run();
            } else {
            	System.err.println("Please ENTER \"Server\", \"Client\" or \"Worker\"!");
            }
        } catch (RemoteException e) {
            System.err.println(e);
        } catch (NotBoundException e) {
            System.err.println(e);
        }
        
    }

}
