yPos <- 0.0;
 
draw <- function() {
  yPos <- yPos - 1.0;
  if (yPos < 0) {
    yPos <- 100;
  }
  processing$line(0, yPos, width, yPos);
}
