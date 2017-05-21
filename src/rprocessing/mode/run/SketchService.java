package rprocessing.mode.run;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author github.com/gaocegege
 */
public interface SketchService extends Remote {

  void startSketch(PdeSketch sketch) throws RemoteException;

  void stopSketch() throws RemoteException;

  void shutdown() throws RemoteException;
}
