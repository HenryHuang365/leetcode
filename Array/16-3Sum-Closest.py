# Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target.

# Return the sum of the three integers.

# You may assume that each input would have exactly one solution.

 

# Example 1:

# Input: nums = [-1,2,1,-4], target = 1
# Output: 2
# Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
# Example 2:

# Input: nums = [0,0,0], target = 1
# Output: 0
# Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).
 

# Constraints:

# 3 <= nums.length <= 500
# -1000 <= nums[i] <= 1000
# -104 <= target <= 104

def threeSumClosest(self, nums, target):
    """
    :type nums: List[int]
    :type target: int
    :rtype: int
    """
    nums = sorted(nums)
    result = nums[0] + nums[1] + nums[2]

    for i in range(0, len(nums) - 2):
        j = i + 1
        k = len(nums) - 1

        while (j < k):
            sum = nums[i] + nums[j] + nums[k]

            if sum == target:
                return sum

            if abs(sum - target) < abs(result - target):
                result = sum

            if sum > target:
                k -= 1
            elif sum < target:
                j += 1

    return result
