package rprocessing.mode.run;

import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import processing.app.Base;
import processing.app.Platform;
import processing.app.Sketch;
import processing.core.PApplet;
import rprocessing.RunnableSketch;
import rprocessing.mode.DisplayType;
import rprocessing.mode.library.LibraryImporter;

/**
 * A sketch run from the PDE.
 * 
 * This is created in the PDE process, serialized, and then used in the sketch process.
 * 
 * @author github.com/gaocegege
 */
@SuppressWarnings("serial")
public class PdeSketch implements RunnableSketch, Serializable {

  private final File mainFile;
  private final String mainCode;
  private final File sketchHome;
  private final File realSketchPath;
  private final Point location;
  private final LocationType locationType;
  private final DisplayType displayType;
  private final List<File> libraryDirs;

  public final String[] codeFileNames; // unique to PdeSketch - leave as public field?

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  private static void log(final String msg) {
    if (VERBOSE) {
      System.err.println(LibraryImporter.class.getSimpleName() + ": " + msg);
    }
  }

  public PdeSketch(final Sketch sketch, final File sketchPath, final DisplayType displayType,
      final Point location, final LocationType locationType) {

    this.displayType = displayType;
    this.location = location;
    this.locationType = locationType;

    this.mainFile = sketchPath.getAbsoluteFile();
    this.mainCode = sketch.getMainProgram();
    this.sketchHome = sketch.getFolder().getAbsoluteFile();
    this.realSketchPath = sketchPath;

    final String[] codeFileNames = new String[sketch.getCodeCount()];
    for (int i = 0; i < codeFileNames.length; i++) {
      codeFileNames[i] = sketch.getCode(i).getFile().getName();
    }
    this.codeFileNames = codeFileNames;

    final List<File> libraryDirs = new ArrayList<>();
    libraryDirs.add(Platform.getContentFile("modes/java/libraries"));
    libraryDirs.add(Base.getSketchbookLibrariesFolder());
    libraryDirs.add(sketchPath);
    this.libraryDirs = libraryDirs;
  }

  public static enum LocationType {
    EDITOR_LOCATION, SKETCH_LOCATION;
  }

  @Override
  public File getMainFile() {
    return mainFile;
  }

  @Override
  public String getMainCode() {
    return mainCode;
  }

  @Override
  public List<File> getLibraryDirectories() {
    return libraryDirs;
  }

  @Override
  public File getHomeDirectory() {
    return mainFile.getParentFile();
  }

  @Override
  public String[] getPAppletArguments() {
    final List<String> args = new ArrayList<>();

    args.add(PApplet.ARGS_EXTERNAL);
    args.add(PApplet.ARGS_SKETCH_FOLDER + "=" + sketchHome);

    switch (displayType) {
      case WINDOWED:
        if (locationType == LocationType.EDITOR_LOCATION) {
          args.add(String.format("%s=%d,%d", PApplet.ARGS_EDITOR_LOCATION, location.x, location.y));
        } else if (locationType == LocationType.SKETCH_LOCATION) {
          args.add(String.format("%s=%d,%d", PApplet.ARGS_LOCATION, location.x, location.y));
        }
        break;
      case PRESENTATION:
        args.add("fullScreen");
        break;
      default:
        // TODO: make the error message more readable.
        System.err.print("Wrong display type.");
    }

    args.add(mainFile.getName()); // sketch name; has to be last argument

    return args.toArray(new String[0]);
  }

  @Override
  public boolean shouldRun() {
    return true;
  }
}
