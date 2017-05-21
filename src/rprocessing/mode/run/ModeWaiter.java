package rprocessing.mode.run;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rprocessing.exception.RMIRuntimeException;

/**
 * 
 * @author github.com/gaocegege
 */
public interface ModeWaiter extends Remote {
  void modeReady(ModeService modeService) throws RemoteException, RMIRuntimeException;
}
