# Processing.R Design and Architecture

## Overview

Processing.R is an R language mode for Processing and the Processing Development Environment (PDE). With Processing.R users can write Processing sketches in the PDE using the R language.

[Processing](http://processing.org/) is a flexible software sketchbook and a language for learning how to code within the context of the visual arts. Since 2001, Processing has promoted software literacy within the visual arts and visual literacy within technology. There are tens of thousands of students, artists, designers, researchers, and hobbyists who use Processing for learning and prototyping.

[The Processing Development Environment (PDE)](https://processing.org/reference/environment/) makes it easy to write Processing programs. Programs are written in the Text Editor and started by pressing the Run button. In Processing, a computer program is called a sketch. Sketches are stored in the Sketchbook, which is a folder on your computer.

[Programming modes](https://processing.org/reference/environment/#Programming_modes) in Processing make it possible to deploy sketches on different platforms and program in different ways. The default is Java mode. Other programming modes include [python mode](http://py.processing.org/), [native javascript mode](https://p5js.org/) and [android mode](http://android.processing.org/). These modes allow users of a specific language to get started with Processing easily.

[R](https://www.r-project.org/) is a language and free software environment for statistical computing and graphics. It compiles and runs on a wide variety of UNIX platforms, Windows and MacOS.

[renjin](http://www.renjin.org/) is a JVM-based interpreter for the R language for statistical computing. Processing.R uses renjin to run R language sketches in JVM using Processing's default Java API. The Processing.R mode has a similiar architecture to [python mode](http://py.processing.org/), which uses Jython.

## Architecture

Processing.R is available in two forms:

* The Processing.R JAR wrapper, `runner.jar`.
* The Processing.R PDE language mode, `RLangMode`.

These two forms share the same architecture.

### COMMON

#### `RLangPApplet`

`RLangPApplet` is a class that extends `BuiltinApplet`. `RLangPApplet` overrides built-in functions such as `draw`, `settings` and `setup`. `RLangPApplet` calls renjin, which is the JVM interpreter for R, to evaluate the R code.

#### `BuiltinApplet`

`BuiltinApplet` is the layer that maps between the Processing Java API and the R language as implemented in renjin. For example, it defines a function to cast parameters from integer to double in order to fit the Processing API.

### JAR

Want to understand `runner.jar`? Start with `Runner`.

#### `Runner`

`Runner` is the class to run Processing.R sketches. It initializes an `RLangPApplet` instance and calls `runSketchBlocking` to run a Processing.R sketch. The entry of `runner.jar` is the `main` function in `Runner`.

#### `SketchRunner`

`SketchRunner` interacts with `Runner` to run the Processing.R sketches. It is the middle layer between `Runner` and `SketchServiceRunner`. 

#### `SketchServiceRunner`

`SketchServiceRunner` is a standalone process out of the mode. It calls the `main` function in `SketchRunner`.

### PDE MODE

Want to understand the Processing Development Environment(PDE) mode? Start with `RLangMode`.

#### `RLangMode`

`RLangMode` is the main class for the Processing.R PDE mode. It is accessed through reflection. Processing.R uses Java Remote Method Invocation(RMI), to solve the performance problem. Processing.R mode creates some long-running processes(`SketchServiceRunner`) to handle the requests from Processing Development Environment(PDE).

#### `RLangEditor`

`RLangEditor` is the class that represents the built-in text editor for R mode in the Processing Development Environment(PDE). It extends PDE's `Editor`, builds the mode editor, and initializes all related components, such as a formatter toolbar.

#### `ModeService` and `SketchServiceManager`

`ModeService` is a Remote Method Invocation(RMI) interface, which extends `java.rmi.Remote`. `SketchServiceManager` is the class which implements `ModeService`. `RLangMode` creates a `SketchServiceManager` instance when started. `SketchServiceManager` manages all `SketchServiceRunner`s, and `SketchServiceRunner` runs the real logic of Processing.R sketches.

## Reference

* "Processing.org". Processing.org. N.p., 2017. Web. 17 June 2017.
