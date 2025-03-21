package Java;
// Given a string s, return the longest

// palindromic

// substring
// in s.

// Example 1:

// Input: s = "babad"
// Output: "bab"
// Explanation: "aba" is also a valid answer.
// Example 2:

// Input: s = "cbbd"
// Output: "bb"

// Constraints:

// 1 <= s.length <= 1000
// s consist of only digits and English letters.
class Solution {
    public String longestPalindrome(String s) {
        String res = "";
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            // odd
            int l = i;
            int r = i;
            while (l >= 0 && r <= s.length() - 1 && s.charAt(l) == s.charAt(r)) {
                if (r - l + 1 > max) {
                    res = s.substring(l, r + 1);
                    max = r - l + 1;
                }
                l--;
                r++;
            }
            // even
            l = i;
            r = i + 1;
            while (l >= 0 && r <= s.length() - 1 && s.charAt(l) == s.charAt(r)) {
                if (r - l + 1 > max) {
                    res = s.substring(l, r + 1);
                    max = r - l + 1;
                }
                l--;
                r++;
            }
        }
        return res;
    }
}