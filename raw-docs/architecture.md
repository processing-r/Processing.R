# Processing.R Design and Architecture

## Overview

[Processing](http://processing.org/) is a flexible software sketchbook and a language for learning how to code within the context of the visual arts. Since 2001, Processing has promoted software literacy within the visual arts and visual literacy within technology. There are tens of thousands of students, artists, designers, researchers, and hobbyists who use Processing for learning and prototyping.

The Processing Development Environment (PDE) makes it easy to write Processing programs. Programs are written in the Text Editor and started by pressing the Run button. In Processing, a computer program is called a sketch. Sketches are stored in the Sketchbook, which is a folder on your computer.

Processing has different programming modes to make it possible to deploy sketches on different platforms and program in different ways. The Java mode is the default. Other programming modes such as [python mode](http://py.processing.org/), [native javascript mode](https://p5js.org/) and [android mode](http://android.processing.org/). These modes allows users with specific language to get started with Processing easily.

Processing.R is an [R language](https://www.r-project.org/) mode for Processing, which has a similiar architecture to [python mode](http://py.processing.org/). R is a free software environment for statistical computing and graphics. It compiles and runs on a wide variety of UNIX platforms, Windows and MacOS. With Processing.R, users could write Processing sketches using R in PDE.

## Architecture

Processing.R has two artifacts:

* A JAR `runner.jar`, which wraps Processing.R into a JAR.
* A mode in Processing `RLangMode`, which will be moved into PDE and act as a language mode to serve PDE users.

The two artifacts share the same architecture.

### `RLangPApplet`

`RLangPApplet` is the class which extends `BuiltinApplet`. `RLangPApplet` overrides the built-in functions such as `draw`, `settings` and `setup`. `BuiltinApplet` is the layer to adapt Processing API to R. For example, it defines a function to cast parameters from integer to double, to fit the API definition in Processing.

`RLangPApplet` calls renjin, which is the JVM interpreter for R, to evaluate the R code.

### `Runner`

`Runner` is the class to run Processing.R sketches. It initializes an `RLangPApplet` instance and calls `runSketchBlocking` to run the Processing.R skectch.

The entry of `runner.jar` is the `main` function in `Runner`. If you want to get familiar with `runner.jar`, you could start from `Runner`.

### `RLangMode`

`RLangMode` is the type for Processing.R mode, which will be accessed through reflection. If you want to know how Processing.R works in Processing Development Environment(PDE), you can start from `RLangMode`.

Processing.R uses Java Remote Method Invocation(RMI), to solve the performance problem. Processing.R mode creates some long-running processes(`SketchServiceRunner`) to handle the requests from Processing Development Environment(PDE).

### `RLangEditor`

`RLangEditor` represents the editor in Processing Development Environment(PDE). It consists of the logic about the built-in editor.

### `ModeService` and `SketchServiceManager`

`ModeService` is the Remote Method Invocation(RMI) interface, which extends `java.rmi.Remote`. `SketchServiceManager` is the class which implements `ModeService`. `RLangMode` creates a `SketchServiceManager` instance when started. `SketchServiceManager` manages all `SketchServiceRunner`s, and `SketchServiceRunner` runs the real logic of Processing.R sketches.

### `SketchServiceRunner`

`SketchServiceRunner` is a standalone process out of the mode. It calls `main` function in `SketchRunner`.

### `SketchRunner`

`SketchRunner` is the middle layer between `SketchServiceRunner` and `Runner`. It interacts with `Runner` to run the Processing.R skectches.

## Reference

* "Processing.Org". Processing.org. N. p., 2017. Web. 17 June 2017.
