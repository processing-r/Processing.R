# R Mode for Processing

Something like [processing.py](https://github.com/jdf/processing.py)

WIP now.

## Demo

Run `ant try`, you will get a runner demo in `try/`, and run `java -jar ./try/processing.r.jar <your R script>`.

```
posAX <- 11
posAY <- 22

posBX <- 33
posBY <- 22

processing$line(posAX, posAY, posBX, posBY)
```

The output is:

<img src="./docs/img/demo.png" height="200">
