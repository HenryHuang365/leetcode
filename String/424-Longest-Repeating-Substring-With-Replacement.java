// You are given a string s consisting of only uppercase english characters and an integer k. You can choose up to k characters of the string and replace them with any other uppercase English character.

// After performing at most k replacements, return the length of the longest substring which contains only one distinct character.

// Example 1:

// Input: s = "XYYX", k = 2

// Output: 4
// Explanation: Either replace the 'X's with 'Y's, or replace the 'Y's with 'X's.

// Example 2:

// Input: s = "AAABABB", k = 1

// Output: 5
// Constraints:

// 1 <= s.length <= 1000
// 0 <= k <= s.length

class Solution {
    public int characterReplacement(String s, int k) {
        int[] list = new int[26];
        int res = 0;
        int max = 0;
        int l = 0;

        for (int r = 0; r < s.length(); r++) {
            list[s.charAt(r) - 'A']++;
            max = Math.max(max, list[s.charAt(r) - 'A']);

            // The key to solve this problem is that we need to ensure
            // the largest number of letters need to be changed in this sliding window is
            // less than k.
            // This is because we should not change the letters that have the most
            // occurances.
            while ((r - l + 1) - max > k) {
                list[s.charAt(l) - 'A']--;
                l++;
            }

            res = Math.max(max, r - l + 1);
        }

        return res;
    }
}
