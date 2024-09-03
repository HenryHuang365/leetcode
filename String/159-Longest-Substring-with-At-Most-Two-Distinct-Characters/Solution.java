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
        Map<Character, Integer> cnt = new HashMap<>();
        int ans = 0;
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            Character currChar = s.charAt(j);
            cnt.put(currChar, cnt.getOrDefault(currChar, 0) + 1);
            while (cnt.size() > 2) {
                Character leftChar = s.charAt(i);
                cnt.put(leftChar, cnt.get(leftChar) - 1);
                if (cnt.get(leftChar) == 0) cnt.remove(leftChar);
                i++;
            }
            ans = Math.max(ans, j - i + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.lengthOfLongestSubstringTwoDistinct("eceba"));
    }
}
