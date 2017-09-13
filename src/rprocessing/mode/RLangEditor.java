package rprocessing.mode;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import processing.app.Base;
import processing.app.Formatter;
import processing.app.Language;
import processing.app.Messages;
import processing.app.Mode;
import processing.app.Platform;
import processing.app.SketchCode;
import processing.app.SketchException;
import processing.app.syntax.JEditTextArea;
import processing.app.syntax.PdeTextAreaDefaults;
import processing.app.ui.Editor;
import processing.app.ui.EditorException;
import processing.app.ui.EditorState;
import processing.app.ui.EditorToolbar;
import processing.app.ui.Toolkit;
import rprocessing.IOUtil;
import rprocessing.mode.run.PdeSketch;
import rprocessing.mode.run.PdeSketch.LocationType;
import rprocessing.mode.run.SketchServiceManager;
import rprocessing.mode.run.SketchServiceRunner;
import rprocessing.util.Constant;

/**
 * RLangEditor is the editor abstraction in R mode, which builds a editor and initialize all related
 * components such as formatter toolbar.
 * 
 * @author github.com/gaocegege
 */
public class RLangEditor extends Editor {

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  /**  */
  private static final long serialVersionUID = -5993950683909551427L;

  private final String id;

  private final RLangMode rMode;

  private final SketchServiceRunner sketchService;

  /**
   * If the user runs a dirty sketch, we create a temp dir containing the modified state of the
   * sketch and run it from there. We keep track of it in this variable in order to delete it when
   * done running.
   */
  private Path tempSketch;

  private static void log(final String msg) {
    if (VERBOSE) {
      System.err.println(RLangEditor.class.getSimpleName() + ": " + msg);
    }
  }

  public String getId() {
    return id;
  }

  protected RLangEditor(Base base, String path, EditorState state, Mode mode)
      throws EditorException {
    super(base, path, state, mode);
    log("Initialize RLangEditor now.");

    id = UUID.randomUUID().toString();

    rMode = (RLangMode) mode;
    // Create a sketch service affiliated with this editor.
    final SketchServiceManager sketchServiceManager = rMode.getSketchServiceManager();
    sketchService = sketchServiceManager.createSketchService(this);

    // Ensure that the sketch service gets properly destroyed when either the
    // JVM terminates or this editor closes, whichever comes first.
    final Thread cleanup = new Thread(new Runnable() {
      @Override
      public void run() {
        sketchServiceManager.destroySketchService(RLangEditor.this);
      }
    });
    Runtime.getRuntime().addShutdownHook(cleanup);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(final WindowEvent e) {
        cleanup.start();
        Runtime.getRuntime().removeShutdownHook(cleanup);
      }
    });
  }

  /**
   * @see processing.app.ui.Editor#buildFileMenu()
   */
  @Override
  public JMenu buildFileMenu() {
    final String appTitle = Language.text("Export Application");
    final JMenuItem exportApplication = Toolkit.newJMenuItem(appTitle, 'E');
    exportApplication.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        handleExportApplication();
      }
    });
    return buildFileMenu(new JMenuItem[] {exportApplication});
  }

  /**
   * @see processing.app.ui.Editor#buildHelpMenu()
   */
  @Override
  public JMenu buildHelpMenu() {
    final JMenu menu = new JMenu("Help");
    menu.add(new JMenuItem(new AbstractAction("About Processing.R") {

      /**  */
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        Platform.openURL("https://processing-r.github.io/");
      }
    }));
    menu.add(new JMenuItem(new AbstractAction("Contribute to Processing.R") {
      /**  */
      private static final long serialVersionUID = 2312501910971341647L;

      @Override
      public void actionPerformed(final ActionEvent e) {
        Platform.openURL("http://github.com/gaocegege/Processing.R");
      }
    }));

    JMenuItem item = new JMenuItem("Tutorials");
    item.setEnabled(false);
    menu.add(item);

    item = new JMenuItem("Getting Started");
    item.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Platform.openURL("https://processing-r.github.io/tutorials/gettingstarted/");
      }
    });
    menu.add(item);
    return menu;
  }

  /**
   * @see processing.app.ui.Editor#buildSketchMenu()
   */
  @Override
  public JMenu buildSketchMenu() {
    final JMenuItem runItem = Toolkit.newJMenuItem(Language.text("toolbar.run"), 'R');
    runItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        handleRun();
      }
    });

    final JMenuItem presentItem = Toolkit.newJMenuItemShift(Language.text("toolbar.present"), 'R');
    presentItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        handlePresent();
      }
    });

    final JMenuItem stopItem = new JMenuItem(Language.text("toolbar.stop"));
    stopItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        handleStop();
      }
    });

    return buildSketchMenu(new JMenuItem[] {runItem, presentItem, stopItem});
  }

  /**
   * @see processing.app.ui.Editor#createFormatter()
   */
  @Override
  public Formatter createFormatter() {
    return null;
  }

  @Override
  protected JEditTextArea createTextArea() {
    return new JEditTextArea(new PdeTextAreaDefaults(mode), new RLangInputHandler(this));
  }

  /**
   * @see processing.app.ui.Editor#createToolbar()
   */
  @Override
  public EditorToolbar createToolbar() {
    return new RLangToolbar(this);
  }

  /**
   * @see processing.app.ui.Editor#deactivateRun()
   */
  @Override
  public void deactivateRun() {
    log("deactivateRun");
    restoreToolbar();
    cleanupTempSketch();
  }

  @Override
  public void handleAutoFormat() {
    super.handleAutoFormat();
    recolor();
  }

  /**
   * @see processing.app.ui.Editor#getCommentPrefix()
   */
  @Override
  public String getCommentPrefix() {
    return "# ";
  }

  @Override
  protected void handleCommentUncomment() {
    super.handleCommentUncomment();
    recolor();
  }

  /**
   * It is empty but cannot be removed because it is a abstract function.
   * 
   * @see processing.app.ui.Editor#handleImportLibrary(java.lang.String)
   */
  @Override
  public void handleImportLibrary(String libraryName) {
    // TODO: Support import specific library.
  }

  /**
   * @see processing.app.ui.Editor#internalCloseRunner()
   */
  @Override
  public void internalCloseRunner() {
    try {
      sketchService.stopSketch();
    } catch (final SketchException exception) {
      statusError(exception);
    } finally {
      cleanupTempSketch();
    }
  }

  /*
   * Helper functions
   */

  public void printOut(final String msg) {
    console.message(msg, false);
  }

  public void printErr(final String msg) {
    console.message(msg, true);
  }

  public void handleRun() {
    runSketch(DisplayType.WINDOWED);
  }

  public void handlePresent() {
    runSketch(DisplayType.PRESENTATION);
  }

  public void handleStop() {
    log("handleStop");
    toolbar.activateStop();
    internalCloseRunner();
    restoreToolbar();
    requestFocus();
  }

  private void recolor() {
    textarea.getDocument().tokenizeLines();
  }

  private void restoreToolbar() {
    toolbar.deactivateStop();
    toolbar.deactivateRun();
    toFront();
  }

  private void runSketch(final DisplayType displayType) {
    prepareRun();
    toolbar.activateRun();
    final File sketchPath;
    if (sketch.isModified()) {
      log("Sketch is modified; must copy it to temp dir.");
      final String sketchMainFileName = sketch.getCode(0).getFile().getName();
      try {
        tempSketch = createTempSketch();
        sketchPath = tempSketch.resolve(sketchMainFileName).toFile();
      } catch (final IOException exception) {
        Messages.showError("Sketchy Behavior",
            "I can't copy your unsaved work\n" + "to a temp directory.", exception);
        return;
      }
    } else {
      sketchPath = sketch.getCode(0).getFile().getAbsoluteFile();
    }

    final LocationType locationType;
    final Point location;
    if (getSketchLocation() != null) {
      locationType = LocationType.SKETCH_LOCATION;
      location = new Point(getSketchLocation());
    } else { // assume editor has a position - is that safe?
      locationType = LocationType.EDITOR_LOCATION;
      location = new Point(getLocation());
    }

    try {
      sketchService
          .runSketch(new PdeSketch(sketch, sketchPath, displayType, location, locationType));
    } catch (final SketchException exception) {
      statusError(exception);
    }
  }

  private void cleanupTempSketch() {
    if (tempSketch != null) {
      if (tempSketch.toFile().exists()) {
        try {
          log("Deleting " + tempSketch);
          IOUtil.rm(tempSketch);
          log("Deleted " + tempSketch);
          assert (!tempSketch.toFile().exists());
        } catch (final IOException exception) {
          System.err.println(exception);
        }
      }
      tempSketch = null;
    }
  }

  /**
   * TODO(James Gilles): Create this! Create export GUI and hand off results to performExport()
   */
  public void handleExportApplication() {
    // Leaving this here because it seems like it's more the editor's responsibility
    if (sketch.isModified()) {
      final Object[] options = {"OK", "Cancel"};
      final int result = JOptionPane.showOptionDialog(this, "Save changes before export?", "Save",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
      if (result == JOptionPane.OK_OPTION) {
        handleSave(true);
      } else {
        statusNotice("Export canceled, changes must first be saved.");
      }
    }
  }

  /**
   * Get the reference from web, not local.
   * 
   * @see processing.app.ui.Editor#handleFindReference()
   */
  @Override
  protected void handleFindReference() {
    String ref = referenceCheck(true);
    if (ref != null) {
      // Remove _.
      if (ref.charAt(ref.length() - 1) == '_') {
        ref = ref.substring(0, ref.length() - 1);
      }
      // Get the reference from the home page, not in local.
      String item = new String(Constant.PROCESSINGR_URL + Constant.REFERENCE_NAME + ref + ".html");
      Platform.openURL(item);
    } else {
      String text = textarea.getSelectedText().trim();
      if (text.length() == 0) {
        statusNotice(Language.text("editor.status.find_reference.select_word_first"));
      } else {
        statusNotice(Language.interpolate("editor.status.find_reference.not_available", text));
      }
    }
  }

  /**
   * Save the current state of the sketch code into a temp dir, and return the created directory.
   * 
   * @return a new directory containing a saved version of the current (presumably modified) sketch
   *         code.
   * @throws IOException
   */
  private Path createTempSketch() throws IOException {
    final Path tmp = Files.createTempDirectory(sketch.getName());
    for (final SketchCode code : sketch.getCode()) {
      Files.write(tmp.resolve(code.getFileName()), code.getProgram().getBytes("utf-8"));
    }
    return tmp;
  }
}
