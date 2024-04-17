# 18. 4Sum
# Medium
# Topics
# Companies
# Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:

# 0 <= a, b, c, d < n
# a, b, c, and d are distinct.
# nums[a] + nums[b] + nums[c] + nums[d] == target
# You may return the answer in any order.

 

# Example 1:

# Input: nums = [1,0,-1,0,-2,2], target = 0
# Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
# Example 2:

# Input: nums = [2,2,2,2,2], target = 8
# Output: [[2,2,2,2]]
 

# Constraints:

# 1 <= nums.length <= 200
# -109 <= nums[i] <= 109
# -109 <= target <= 109

def fourSum(self, nums, target):
    """
    :type nums: List[int]
    :type target: int
    :rtype: List[List[int]]
    """
    def findNsum(nums, target, N, result, results):
        if len(nums) < N or N < 2 or target < nums[0] * N or target > nums[-1] * N: 
            return

        if N == 2: 
            l = 0
            r = len(nums) - 1
            while l < r: 
                sum = nums[l] + nums[r]
                if sum == target: 
                    results.append(result + [nums[l], nums[r]])

                    l += 1
                    r -= 1
                    while l < r and nums[l - 1] == nums[l]:
                        l += 1
                    while l < r and nums[r + 1] == nums[r]:
                        r -= 1
                elif sum < target:
                    l += 1
                else:
                    r -= 1
        else:
            for i in range(len(nums) - N + 1):
                if i == 0 or (i > 0 and nums[i - 1] != nums[i]):
                    findNsum(nums[i + 1:], target - nums[i], N - 1, result + [nums[i]], results)


    results = []
    findNsum(sorted(nums), target, 4, [], results)
    return results
    