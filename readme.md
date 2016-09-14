# R Mode for Processing

Something like [processing.py](https://github.com/jdf/processing.py)

WIP now.

## Demo

### Editor

Change `processing.modes` in build.xml, and run `ant install`, you will get a mode in your `processing.modes`.

<img src="./docs/img/editor.png" width="600">

<img src="./docs/img/demo.gif" width="600">

### Runner.jar

Run `ant try`, you will get a runner demo in `try/`, and run `java -jar ./try/RLangMode.jar <your R script>`.

```
posAX <- 11
posAY <- 22

posBX <- 33
posBY <- 22

processing$line(posAX, posAY, posBX, posBY)
```

The output is:

<img src="./docs/img/demo.png" height="200">
