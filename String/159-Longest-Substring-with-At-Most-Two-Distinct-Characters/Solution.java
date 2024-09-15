// Given a string s , find the length of the longest substring t  that contains at most 2 distinct characters.

// Example 1:

// Input: "eceba"
// Output: 3
// Explanation: t is "ece" which its length is 3.
// Example 2:

// Input: "ccaabbb"
// Output: 5
// Explanation: t is "aabbb" which its length is 5.

import java.util.Map;
import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        int res = 0;
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            Character rightChar = s.charAt(right);
            counts.put(rightChar, counts.getOrDefault(rightChar, 0) + 1);
            while (counts.size() > 2) {
                Character leftChar = s.charAt(left);
                counts.put(leftChar, counts.getOrDefault(leftChar, 0) - 1);
                if (counts.get(leftChar) == 0)
                    counts.remove(leftChar);
                left++;
            }

            res = Math.max(res, right - left + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.lengthOfLongestSubstringTwoDistinct("eceba"));
    }
}
