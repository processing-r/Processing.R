package rprocessing.mode.library;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngine;

import com.google.common.base.Joiner;

public class LibraryImporter {

  /*
   * Directories where libraries may be found.
   */
  private final List<File> libSearchPath;

  private final RenjinScriptEngine renjinScriptEngine;

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  private static void log(final String msg) {
    if (VERBOSE) {
      System.err.println(LibraryImporter.class.getSimpleName() + ": " + msg);
    }
  }

  public LibraryImporter(final List<File> libdirs, final RenjinScriptEngine renjinScriptEngine) {
    this.libSearchPath = libdirs;
    this.renjinScriptEngine = renjinScriptEngine;
  }

  public void loadLibraries(final Set<String> libs) throws ScriptException {
    for (String lib : libs) {
      this.loadLibrary(lib);
    }
  }

  private void loadLibrary(final String libName) throws ScriptException {
    for (final File searchDir : libSearchPath) {
      log(searchDir.getAbsolutePath());
      final File handRolledJar =
          new File("/home/ist/sketchbook/libraries/peasycam/library/peasycam.jar");
      if (handRolledJar.exists()) {
        log("Found hand-rolled jar lib " + handRolledJar);
        addJarToClassLoader(handRolledJar);
        importPublicClassesFromJar(handRolledJar);
        return;
      }
    }
  }

  /**
   * Use a brittle and egregious hack to forcibly add the given jar file to the system classloader.
   * 
   * @param jar The jar to add to the system classloader.
   */
  private void addJarToClassLoader(final File jar) {
    try {
      final URL url = jar.toURI().toURL();
      final URLClassLoader ucl = (URLClassLoader) ClassLoader.getSystemClassLoader();
      // Linear search for url. It's ok for this to be slow.
      for (final URL existing : ucl.getURLs()) {
        if (existing.equals(url)) {
          return;
        }
      }
      log("Appending " + url + " to the system classloader.");
      final Method addUrl = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      addUrl.setAccessible(true);
      addUrl.invoke(ucl, url);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException | MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  /*
   * Then create and execute an import statement for each top-level, named class in the given jar
   * file. For example, if the library jar contains classes
   * 
   * com.foo.Banana.class com.foo.Banana$1.class com.foo.Banana$2.class com.bar.Kiwi.class
   * 
   * then we'll generate these import statements:
   * 
   * from com.foo import Banana from com.bar import Kiwi
   */
  private void importPublicClassesFromJar(final File jarPath) throws ScriptException {
    log("Importing public classes from " + jarPath.getAbsolutePath());
    try (final ZipFile file = new ZipFile(jarPath)) {
      final Enumeration<? extends ZipEntry> entries = file.entries();
      while (entries.hasMoreElements()) {
        final ZipEntry entry = entries.nextElement();
        if (entry.isDirectory()) {
          continue;
        }
        final String name = entry.getName();
        if (!name.endsWith(".class")) {
          log("Rejecting non-class " + name);
          continue;
        }
        final int slash = name.lastIndexOf('/');
        if (slash == -1) {
          log("Rejecting " + name);
          continue;
        }
        if (name.contains("$")) {
          log("Rejecting " + name);
          continue;
        }
        final String[] path = name.split("/");
        final String className = path[path.length - 1].replace(".class", "");
        final String packageName =
            Joiner.on(".").join(Arrays.asList(path).subList(0, path.length - 1));

        final String importStatement = String.format("import(%s.%s)", packageName, className);
        log(importStatement);
        this.renjinScriptEngine.eval(importStatement);
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
