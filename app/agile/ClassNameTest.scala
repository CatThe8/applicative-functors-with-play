package agile

object Solution {
  def solution(a: Array[Int]): Int = {
    var arrays: Array[Array[Int]] = Array[Array[Int]]()
    var array: Array[Int] = Array[Int]()
    val distinct = a.distinct

    for (index <- a.indices) {
      val ls1: Array[Int] = a.drop(index)
      val ls2: Array[Int] = a.dropRight(index)

      if (ls1.length >= distinct.length)
        arrays = arrays.appended(ls1)

      if (ls2.length >= distinct.length)
        arrays = arrays.appended(ls2)
    }

    val arrayToTest = arrays.filter(x => x.length >= distinct.length)

    for (arr <- arrayToTest) {
      var distinctElems = distinct.clone()
      var res: Int = 0
      for (i <- arr) {
        res += 1
        if (distinctElems.contains(i)) {
          distinctElems = distinctElems.filter(x => x != i)
        }
        if (distinctElems.isEmpty) {
          array = array.appended(res)
        }
      }
    }
    val result = array.minBy(i => i)
    println("result: " + result)

    result
  }

  def main(args: Array[String]): Unit = {
    solution(Array(7, 3, 7, 3, 1, 3, 4, 1))
    solution(Array(7, 3, 7, 3, 3, 3, 2, 7))
    solution(Array(1, 1, 1, 1, 1, 4, 3, 4, 2, 3, 4, 4, 3, 1, 4, 9, 3))
    solution(Array(5, 4, 3, 5, 5, 4, 4, 5, 3, 2, 1, 7))
    solution(Array(1, 1, 1, 1, 1, 1, 1, 4, 3, 2, 1, 7, 9, 6, 5, 8, 10, 1, 1))
    solution(Array(7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8))
    solution(Array(8, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7))
    solution(Array(8, 7, 7, 7, 7, 7, 7, 7, 7, 7, 5, 7, 8))
    solution(Array(8, 7, 5, 7, 7, 7, 7, 7, 7, 7, 5, 7, 8))
  }
}