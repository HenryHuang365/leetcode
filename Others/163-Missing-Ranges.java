// Given a sorted integer array nums, where the range of elements are in the inclusive range [lower, upper],
// return its missing ranges.

// Example:

// Input: nums = [0, 1, 3, 50, 75], lower = 0 and upper = 99,
// Output: ["2", "4->49", "51->74", "76->99"]
// """
// """
// Algorithm:
// 1. Declare prev_val to store start_val for missing range
// 2. prev_val starts from lower - 1
// 3. Iterate nums,
//     3.a if num == prev_val + 2: previous num is missing and no continous series
//         add str(num-1) to result
//     3.b elif i > prev_val + 2:  add missing range from (prev_val + 1) to (num - 1) to result
//     3.c prev_val = num
// 4. return result
// """
// """
// @param {int[]} nums
// @param {int} lower
// @param {int} upper
// @return {str[]}

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> miss(List<Integer> nums, int lower, int higher) {
        return new ArrayList<>();
    }
    
}