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
    val minimumAccepted = 1
    scala.util.Sorting.quickSort(list)

    val filteredList = list.dropWhile(_ <= 0)
    if (!filteredList.contains(minimumAccepted) && filteredList.exists(_ > minimumAccepted)) return minimumAccepted

    for ((elem, index) <- filteredList.view.zipWithIndex) {
      val nextIndex = index + 1
      val result = elem + 1
      val nextElement = if (nextIndex == filteredList.length) return result else filteredList(nextIndex)
      if (result != nextElement && elem != nextElement) return result
    }
    minimumAccepted
  }
}

object main {
  def main(args: Array[String]): Unit = {
    val list1 = Random.shuffle(Array.range(1, 100000)).toArray
    val list2 = Random.shuffle(Array.range(-1000000, 1000000)).toArray
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
    println("Time start 2: " + (time2 - System.currentTimeMillis()))
  }
}