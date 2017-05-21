package rprocessing.mode.run;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author github.com/gaocegege
 */
public interface ModeService extends Remote {

  void handleReady(String editorId, final SketchService service) throws RemoteException;

  void handleSketchMoved(String editorId, final Point leftTop) throws RemoteException;

  void handleSketchStopped(String editorId) throws RemoteException;

  void handleSketchException(String editorId, Exception exception) throws RemoteException;

  void printStdOut(String editorId, String str) throws RemoteException;

  void printStdErr(String editorId, String str) throws RemoteException;
}
