# HOWTO

## Installation

Processing.R is available for the Processing Development Environment (PDE) or stand-alone. It is available in these forms:

1. a PDE mode, which can be
   -  added to PDE
   -  built with its own PDE
2. a command-line runner -- does not require the PDE
3. a pre-built image -- OLD
4. a docker container image -- DEPRECATED

Processing.R is *not* currently available via PDE > Add Tool > Modes, however the mode will appear there once installed.

### 1. PDE Mode

**Warning:** Many parts of PDE integration are still incomplete: files cannot be double-clicked or dragged to open so code must be cut-pasted into the window, saved files cannot be reopened except through the recent files dialog list, the run button launches multiple window rather than re-running, stop button does not work, etc.

1. checkout Processing.R from github
2. configure `./scripts/generate-ant-file.sh`
3. build and install mode into PDE using `ant build` (must have ant)
4. start PDE and select `R Language` from mode drop-down menu

#### Configure script

Configure `./scripts/generate-ant-file.sh`:

- `modes`: the destination for installing the mode once it is built.  
   -  MacOSX: `/Users/[MyUserName]/Documents/Processing/modes/`
   -  Windows: `%homepath%\Documents\modes\`
   -  Linux: `~/sketchbook/modes/`
- `core` and `pde`: directories contain pde.jar and core.jar. They are be used to build RLangMode.
   -  MacOSX: `/Applications/Processing.app/Contents/Java/`
- `executable`: optional argument giving the location of PDE.
   -  The path is used in `ant run` to start a PDE instance. Leave blank to not launch PDE on `ant run`.
   -  MacOSX: `/Applications/Processing.app/Contents/MacOS/Processing`

##### A) configure for adding to an existing PDE

For example, to install the mode into a default existing PDE app on a MacOS system, set arguments in `./scripts/generate-ant-file.sh` such as:

```
modes="/Users/[MyUserName]/Documents/Processing/modes"
core="/Applications/Processing.app/Contents/Java/"
pde="/Applications/Processing.app/Contents/Java/"
executable="/Applications/Processing.app/Contents/MacOS/Processing"
```

This will generate build.xml errors (as the core and pde directories only contain the actual jars, not source). However it will work correctly.

##### B) configure for creating a new PDE

Build the source code of Processing core and pde wherever it is located on the system. For example:

```bash
$ cd processing/core
$ ant build
$ cd processing/app
$ ant build
```

Then set the two paths accordingly in `./scripts/generate-ant-file.sh`:

```
modes="~/Documents/Processing/modes"
core="../processing/core/library/"
pde="../processing/app/"
executable="/Applications/Processing.app/Contents/MacOS/Processing"
```

#### Install

* Run `./scripts/generate-ant-file.sh` to get a valid build.xml
* Run `ant install`, the modes will be installed into PDE.

<div align="center">
	<img src="./img/editor.png" alt="Editor" width="500">
</div>

<div align="center">
	<img src="./img/demo.gif" alt="Demo" width="300">
</div>


### 2. Command Line Runner

Processing.R offers a jar, which allows to have a try without the installation of Processing app. 

Run `ant try`, you will get a runner in `try/`, and run `java -jar ./try/RLangMode.jar <your R script>`.

```r
posAX <- 11
posAY <- 22

posBX <- 33
posBY <- 22

processing$line(posAX, posAY, posBX, posBY)
```

The output is:

<div align="center">
	<img src="./img/demo.png" alt="Output" width="100">
</div>

### 3. Image (OLD)

**Warning:** The pre-built distribution may be significantly out of date compared to the latest repository.

The distribution image is available from:

-  [gaocegege.com/Processing.R](http://gaocegege.com/Processing.R)

### 4. Docker Image (DEPRECATED)

* `docker pull quay.io/gaocegege/processing.r`
* `docker run quay.io/gaocegege/processing.r`
* Open the link of NoVNC in a web browser and the default password is `process`. Input it in the URL and play with Processing.R in a desktop environment:)

See [the demo in vimeo :)](https://vimeo.com/207571123)
