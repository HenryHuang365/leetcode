# Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.

# Notice that the solution set must not contain duplicate triplets.

 

# Example 1:

# Input: nums = [-1,0,1,2,-1,-4]
# Output: [[-1,-1,2],[-1,0,1]]
# Explanation: 
# nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
# nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
# nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
# The distinct triplets are [-1,0,1] and [-1,-1,2].
# Notice that the order of the output and the order of the triplets does not matter.
# Example 2:

# Input: nums = [0,1,1]
# Output: []
# Explanation: The only possible triplet does not sum up to 0.
# Example 3:

# Input: nums = [0,0,0]
# Output: [[0,0,0]]
# Explanation: The only possible triplet sums up to 0.
 

# Constraints:

# 3 <= nums.length <= 3000
# -105 <= nums[i] <= 105

def threeSum(self, nums):
    """
    :type nums: List[int]
    :rtype: List[List[int]]
    """
    # res = set()

    # # 1. split the nums into 3 arrays of positive, negative and zero
    # n, p, z = [], [], []

    # for num in nums: 
    #     if num > 0: 
    #         p.append(num)
    #     elif num < 0: 
    #         n.append(num)
    #     else:
    #         z.append(num)

    # # 2. create separate set for positives and negatives
    # P, N = set(p), set(n)

    # # 3. If there is at least 1 zero, check if the P and N sets have nums that are opposite
    # if z: 
    #     for i in P:
    #         if -1*(i) in N:
    #             res.add((-1*(i), 0, i))

    # # If z has more than 3 zeros, then add the 3 zeros into the set
    # if len(z) >= 3:
    #     res.add((0, 0, 0))

    # # 4. Iterate through P and find 2 nums, check if there exists a negate num equals to the sum of the two positive nums. 
    # for i in range(0, len(p)): 
    #     for j in range(i + 1, len(p)):
    #         sum = (p[i] + p[j])
    #         if -1 * sum in N:
    #             res.add(tuple(sorted([p[i], p[j], -1*sum])))

    # # 5. Iterate through N and find 2 nums, check if there exists a positive num equals to the sum of the two negative nums. 
    # for i in range(0, len(n) - 1): 
    #     for j in range(i + 1, len(n)):
    #         sum = (n[i] + n[j])
    #         if -1 * sum in P:
    #             res.add(tuple(sorted([n[i], n[j], -1*sum])))

    # return res

    res = []
    nums = sorted(nums)

    for i in range(0, len(nums) - 2):
        if i > 0 and nums[i] == nums[i-1]:
            continue  # skip duplicate value for `i`

        target = -1 * nums[i]
        front = i + 1
        back = len(nums) - 1
        while front < back:
            sum = nums[front] + nums[back]

            if sum < target:
                front += 1
            elif sum > target:
                back -= 1
            else:
                triplet = [nums[i], nums[front], nums[back]]
                res.append(triplet)

                front += 1
                back -= 1
                while front < back and nums[front] == nums[front - 1]:
                    front += 1

                while front < back and nums[back] == nums[back + 1]:
                    back -= 1
    return res
    

    