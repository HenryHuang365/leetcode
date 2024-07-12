// Given a string s, find the length of the longest
// substring
// without repeating characters.

// Example 1:

// Input: s = "abcabcbb"
// Output: 3
// Explanation: The answer is "abc", with the length of 3.
// Example 2:

// Input: s = "bbbbb"
// Output: 1
// Explanation: The answer is "b", with the length of 1.
// Example 3:

// Input: s = "pwwkew"
// Output: 3
// Explanation: The answer is "wke", with the length of 3.
// Notice that the answer must be a substring, "pwke" is a subsequence and not a
// substring.

// Constraints:

// 0 <= s.length <= 5 * 104
// s consists of English letters, digits, symbols and spaces.

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxSubtring = 0;
        Set<Character> subSet = new HashSet<>();

        int l = 0;

        for (int r = 0; r < s.length(); r++) {
            if (!subSet.contains(s.charAt(r))) {
                subSet.add(s.charAt(r));
                maxSubtring = Math.max(maxSubtring, r - l + 1);
            } else {
                while (l <= r && subSet.contains(s.charAt(r))) {
                    subSet.remove(s.charAt(l));
                    l++;
                }
                // Mistake: Always remember to add the next character.
                subSet.add(s.charAt(r));

                // here you do not have to update the max,
                // as the left poiner moving toward right is definite unchanging or reducing the
                // max.
            }
        }

        return maxSubtring;
    }

    public int lengthOfLongestSubstring2(String s) {
        int max = 0;
        int[] charArrays = new int[128];
        Arrays.fill(charArrays, -1);
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            if (charArrays[s.charAt(right)] >= left) {
                left = charArrays[s.charAt(right)] + 1;
            }

            charArrays[s.charAt(right)] = right;
            max = Math.max(max, right - left + 1);
        }
        return max;
    }
}