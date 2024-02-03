# Given an integer x, return true if x is a 
# palindrome
# , and false otherwise.

 

# Example 1:

# Input: x = 121
# Output: true
# Explanation: 121 reads as 121 from left to right and from right to left.
# Example 2:

# Input: x = -121
# Output: false
# Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
# Example 3:

# Input: x = 10
# Output: false
# Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 

# Constraints:

# -231 <= x <= 231 - 1
 

# Follow up: Could you solve it without converting the integer to a string?


def isPalindrome(self, x):
    """
    :type x: int
    :rtype: bool
    """
    # reverse the entire integer
    # This is also the way to reverse an integer. 
    # if x < 0:
    #     return False
    
    # reversed_num = 0
    # temp = x
    
    # while temp != 0:
    #     last_digit = temp % 10
    #     reversed_num = reversed_num * 10 + last_digit
    #     temp //= 10
        
    # return reversed_num == x
    
    
    # reverse half of the integer as palindrome is symmetrical
    if x < 0 or (x != 0 and x % 10 == 0):
        return False
    
    reversed_num = 0
    temp = x
    
    while temp > reversed_num:
        reversed_num = reversed_num * 10 + temp % 10
        temp //= 10
        
    return temp == reversed_num or temp == (reversed_num // 10)