import scala.io.Source

object Question2 {

  def isSafe(levels: Array[Int], direction: String): Boolean = {
    for (i <- 0 until levels.length - 1) {
      val diff = levels(i + 1) - levels(i)
      val absDiff = math.abs(diff)
      if (absDiff < 1 || absDiff > 3) {
        return false
      }
      if (direction == "increasing") {
        if (diff <= 0) {
          return false
        }
      } else if (direction == "decreasing") {
        if (diff >= 0) {
          return false
        }
      } else {
        return false
      }
    }
    return true
  }

  def part1(reports: Array[Array[Int]]): Int = { // Time complexity: O(n x m) and space complexity: O(1)
    var results = 0
    for (levels <- reports) {
      if (isSafe(levels, "increasing") || isSafe(levels, "decreasing")) {
        results += 1
      }
    }
    results
  }

  def part2(reports: Array[Array[Int]]): Int = { // Time complexity: O(n x m^2) and space complexity: O(m)
    var results = 0

    for (levels <- reports) {
      var safe = false

      if (isSafe(levels, "increasing") || isSafe(levels, "decreasing")) {
        safe = true
      } 

      else {
        var i = 0
        while (i < levels.length && !safe) {
          val newLevels = levels.slice(0, i) ++ levels.slice(i + 1, levels.length)
          if (isSafe(newLevels, "increasing") || isSafe(newLevels, "decreasing")) {
            safe = true
          }
          i += 1
        }
      }

      if (safe) {
        results += 1
      }
    }

    results
  }

  def main(args: Array[String]): Unit = {
    val reports = Source.fromFile("input.txt")
      .getLines()
      .map(line => line.split(" ").map(_.toInt))
      .toArray

    println("Part 1: " + part1(reports))
    println("Part 2: " + part2(reports))
  }
}
