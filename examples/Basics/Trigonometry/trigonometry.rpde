# https://processing.org/tutorials/trig/

px <- 0
py <- 0
px2 <- 0
py2 <- 0
angle <- 0
angle2 <- 0
x <- 0
x2 <- 0
radius <- 50
frequency <- 2
frequency2 <- 2

settings <- function()
{
    processing$size(600, 200)
}

draw <- function()
{
    processing$background(127)
    processing$noStroke()
    processing$fill(255)
    processing$ellipse(width/8, 75, radius*2, radius*2)
    # Rotates rectangle around circle
    px <- width/8 + cos(processing$radians(angle))*(radius)
    py <- 75 + sin(processing$radians(angle))*(radius)
    processing$fill(0)

    processing$rect(px, py, 5, 5)
    processing$stroke(100)
    processing$line(width/8, 75, px, py)
    processing$stroke(200)

    # Keep reinitializing to 0, to avoid
    # Flashing during redrawing
    angle2 <- 0

    # Draw static curve - y <- sin(x)
    for (i in 1:width - 1)
    {
        px2 <- width/8 + cos(processing$radians(angle2))*(radius)
        py2 <- 75 + sin(processing$radians(angle2))*(radius)
        processing$point(width/8+radius+i, py2)
        angle2 <- angle2 - frequency2
    }

    # Send small ellipse along sine curve
    # to illustrate relationship of circle to wave
    processing$noStroke()
    processing$ellipse(width/8+radius+x, py, 5, 5)
    angle <- angle - frequency
    x <- x + 1

    # When little ellipse reaches end of window,
    # set the variables back to 0
    if (x >= width-60) {
        x <- 0
        angle <- 0
    }

    # Draw dynamic line connecting circular path with wave
    processing$stroke(50)
    processing$line(px, py, width/8+radius+x, py)

    # Output calculations to screen
    # processing$text("y <- sin x", 35, 185)
    # processing$text("px <- " + px, 105, 185)
    # processing$text("py <- " + py, 215, 185)
}