package exercises

import scala.util.Random

/**
 * This is a demo task.
 *
 * Write a function:
 *
 * class Solution { public int solution(int[] A); }
 *
 * that, given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
 *
 * For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
 *
 * Given A = [1, 2, 3], the function should return 4.
 *
 * Given A = [−1, −3], the function should return 1.
 *
 * Write an efficient algorithm for the following assumptions:
 *
 * N is an integer within the range [1..100,000];
 * each element of array A is an integer within the range [−1,000,000..1,000,000].
 */

object Solution {
  def solution(list: Array[Int]): Int = {
    val minAcceptedSolution = 1
    scala.util.Sorting.quickSort(list)

    val filteredList = list.dropWhile(_ <= 0)
    if (filteredList.headOption.getOrElse(0) != 1) return minAcceptedSolution

    for ((elem, index) <- filteredList.view.zipWithIndex) {
      val nextIndex = index + 1
      val result = elem + 1
      val isLastElem = nextIndex == filteredList.length
      if (isLastElem) return result
      val nextElement =  filteredList(nextIndex)
      if (elem != nextElement &&  result != nextElement) return result
    }
    minAcceptedSolution
  }
}

object main {
  def main(args: Array[String]): Unit = {
    val list1 = Random.shuffle(Array.range(1, 100000)).toArray
    val list2 = Random.shuffle(Array.range(-1000000, 1000000)).toArray
    val list3 = Random.shuffle(Array.range(2, 100000).concat(Array.range(-100000, 0))).toArray

    println(Solution.solution(Array(1, 3, 6, 4, 1, 2)) + " Should be 5")
    println(Solution.solution(Array(6, 3, 1)) + " Should be 2")
    println(Solution.solution(Array(6, 3, 2)) + " Should be 1")
    println(Solution.solution(Array(1, 2, 3)) + " Should be 4")
    println(Solution.solution(Array(-1, -2, -3)) + " Should be 1")
    println(Solution.solution(Array(-1, -2, 2, 3, 5)) + " Should be 1")
    println(Solution.solution(Array(1, 3, 6, 4, 1, 2)) + " Should be 5")
    println(Solution.solution(Array(2, 3, 5, -1, -2, -6)) + " Should be 1")
    println(Solution.solution(Array()) + " Should be 1")
    println(Solution.solution(Array(1)) + " Should be 2")
    println(Solution.solution(Array(0)) + " Should be 1")
    println(Solution.solution(Array(-1)) + " Should be 1")
    println(Solution.solution(Array(1, -1, -2, -5)) + " Should be 2")
    println(Solution.solution(Array(2, 3, 5, 5, 6, 7, -1, -2, -6)) + " Should be 1")
    println(Solution.solution(Array(2, -1, -2, -5)) + " Should be 1")
    println(Solution.solution(Array(2, 2, 2, -1, -2, -5)) + " Should be 1")
    println(Solution.solution(Array(3, -1, -2, -5)) + " Should be 1")
    println(Solution.solution(Array(9, -1, -2, -5)) + " Should be 1")
    val time1 = System.currentTimeMillis()
    println(Solution.solution(list1) + " Should be performant")
    val time2 = System.currentTimeMillis()
    println("Time end 1: " + (time1 - time2))
    println(Solution.solution(list2) + " Should be performant")
    val time3 = System.currentTimeMillis()
    println("Time start 2: " + (time2 - time3))
    println(Solution.solution(list3) + " Should be performant")
    println("Time start 3: " + (time3 - System.currentTimeMillis()))
  }
}