# HOWTO

If you just want to have a try, then there is no need to read this documentation, you could get the distribution from [gaocegege.com/Processing.R](http://gaocegege.com/Processing.R)

## Play with Docker

### DEPRECATED

* `docker pull quay.io/gaocegege/processing.r`
* `docker run quay.io/gaocegege/processing.r`
* Open the link of NoVNC in a web browser and the default password is `process`. Input it in the URL and play with Processing.R in a desktop environment:)

See [the demo in vimeo :)](https://vimeo.com/207571123)

## In Your Native Environment

### Editor Support

1. checkout Processing.R from github
2. configure `./scripts/generate-ant-file.sh`
3. build mode and install into PDE using `ant build` (must have ant)
4. restart PDE
5. select R Language from mode drop-down

**Note:** Many parts of PDE integration are still incomplete: files cannot be double-clicked or dragged to open so code must be cut-pasted into the window, saved files cannot be reopened except through the recent files dialog list, the run button launches multiple window rather than re-running, stop button does not work, etc.

There are 4 paths to be determined in `./scripts/generate-ant-file.sh`:

```
modes="~/Documents/Processing/modes"
core="../processing/core/library/"
pde="../processing/app/"
executable="/Applications/Processing.app/Contents/MacOS/Processing"
```

After that,

* Run `./scripts/generate-ant-file.sh` to get a valid build.xml
* Run `ant install`, the modes will be installed into PDE.

<div align="center">
	<img src="./img/editor.png" alt="Editor" width="500">
</div>

<div align="center">
	<img src="./img/demo.gif" alt="Demo" width="300">
</div>

#### Explanation about paths in `scripts/generate-ant-file.sh`

##### mode

The value of mode could be:

* MacOSX: `~/Documents/Processing/modes/`
* Windows: `%homepath%\Documents\modes\`
* Linux: `~/sketchbook/modes/`

##### pde and core

These are two directories which contain pde.jar and core.jar. They are be used to build RLangMode.

You could get the two directories after building the source code of Processing:

```bash
$ cd processing/core
$ ant build
$ cd processing/app
$ ant build
```

Then you could set the two paths:

```
core="processing/core/library/"
pde="processing/app/"
```

##### executable

The path is used in `ant run`, it will call the binary to start a PDE instance, if you don't want this, feel free to leave it blank.

### Runner.jar

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
