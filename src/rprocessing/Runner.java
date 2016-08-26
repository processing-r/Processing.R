//package rprocessing;
//
//import rprocessing.util.Printer;
//import rprocessing.util.StreamPrinter;
//
//public class Runner {
//   public static RunnableSketch sketch;
//
//   public static void main(final String[] args) throws Exception {
//       if (args.length < 1) {
//           throw new RuntimeException("I need the path of your R script as an argument.");
//       }
//       try {
//           sketch = new StandaloneSketch(args);
//           runSketchBlocking(sketch, new StreamPrinter(System.out), new StreamPrinter(System.err));
//       } catch (final Throwable t) {
//           System.err.println(t);
//           System.exit(-1);
//       }
//   }
//
//   public synchronized static void runSketchBlocking(final RunnableSketch sketch,
//                                                     final Printer stdout, final Printer stderr)
//                                                                                                throws PythonSketchError {
//       runSketchBlocking(sketch, stdout, stderr, null);
//   }
//
//   public synchronized static void runSketchBlocking(final RunnableSketch sketch,
//                                                     final Printer stdout,
//                                                     final Printer stderr,
//                                                     final SketchPositionListener sketchPositionListener)
//                                                                                                         throws PythonSketchError {
//   }
//}
