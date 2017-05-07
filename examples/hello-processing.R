processing$line(11, 22, 33, 22)

processing$arc(50, 55, 50, 50, 0, 100)

processing$triangle(30, 75, 58, 20, 86, 75)

processing$ellipse(56, 46, 55, 55)

processing$quad(38, 31, 86, 20, 69, 63, 30, 76)

processing$bezierDetail(as.integer(12))

bezier(85, 20, 10, 10, 90, 90, 15, 80)

# for (i in 1:16) {
#   t = i / float(steps);
#   x = processing$bezierPoint(85, 10, 90, 15, t);
#   y = processing$bezierPoint(20, 10, 90, 80, t);
#   tx = processing$bezierTangent(85, 10, 90, 15, t);
#   ty = processing$bezierTangent(20, 10, 90, 80, t);
#   a = atan2(ty, tx);
#   a -= HALF_PI;
#   line(x, y, cos(a)*8 + x, sin(a)*8 + y);
# }

# bezierPoint
for (i in 1:10) {
  t = i / 10
  x = processing$bezierPoint(85, 10, 90, 15, t)
  y = processing$bezierPoint(20, 10, 90, 80, t)
  processing$ellipse(x, y, 5, 5)
}

processing$curve(5, 26, 73, 24, 73, 61, 15, 65)

stdout$print("Hello, Processing.R")
