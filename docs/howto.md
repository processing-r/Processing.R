# HOWTO

### Editor Support

Change `processing.modes` in build.xml, and run `ant install`, you will get a mode in `processing/modes`.

```xml
<!-- folder to install modes in (probably a folder called "modes" inside your sketchbook folder) -->
<property name="processing.modes" location="/Users/<username>/Documents/Processing/modes" />
<!-- path to your processing executable. -->
<property name="processing.executable" location="/Applications/Processing.app/Contents/MacOS/Processing" />
```

<img src="./docs/img/editor.png" width="600">

<img src="./docs/img/demo.gif" width="600">

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

<img src="./docs/img/demo.png" height="200">
