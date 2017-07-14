# HOWTO

The mode could be downloaded from [Processing.R Release Page](https://github.com/gaocegege/Processing.R/releases)

After you download the mode, place it into Processing "modes" directory:

- macOS: `${HOME}/Documents/Processing/modes`
- Linux: `${HOME}/sketchbook/modes`
- Windows: `C:\Users\<user>\Documents\Processing\modes`

Now the features in Processing.R include:

## Limitations in Processing.R

Processing.R is in active development as an experimental pre-release version.

**Static sketches:** Processing.R does not have a good support for detecting static/active/mix mode. We recommend that all sketches be written in full active mode, defining a separate `settings`, `setup` and `draw`. Even simple sketches should be wrapped in `draw()`. For example, do not write:

```R
line(0, 10, 90, 100)
```

That may cause bugs. Instead, write:

```R
draw <- function() {
    line(0, 10, 90, 100)
}
```

Please try our experimental mode and give us your feedback :) You could leave your comments in [#142](https://github.com/gaocegege/Processing.R/issues/142) or come chat at the [Processing.R gitter channel](https://gitter.im/gaocegege/Processing.R) :tada: 
