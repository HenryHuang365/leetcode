// Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.

// In other words, return true if one of s1's permutations is the substring of s2.

// Example 1:

// Input: s1 = "ab", s2 = "eidbaooo"
// Output: true
// Explanation: s2 contains one permutation of s1 ("ba").
// Example 2:

// Input: s1 = "ab", s2 = "eidboaoo"
// Output: false

// Constraints:

// 1 <= s1.length, s2.length <= 104
// s1 and s2 consist of lowercase English letters.

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'A']++;
            s2Count[s2.charAt(i) - 'A']++;
        }

        int match = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Count[i] == s2Count[i]) {
                match++;
            }
        }

        int l = 0;
        for (int r = s1.length(); r < s2.length(); r++) {
            if (match == 26) {
                return true;
            }

            s2Count[s2.charAt(r) - 'A']++;
            if (s2Count[s2.charAt(r) - 'A'] == s1Count[s2.charAt(r) - 'A']) {
                match++;
            } else if (s2Count[s2.charAt(r) - 'A'] - 1 == s1Count[s2.charAt(r) - 'A']) {
                match--;
            }

            s2Count[s2.charAt(l) - 'A']--;
            if (s2Count[s2.charAt(l) - 'A'] == s1Count[s2.charAt(l) - 'A']) {
                match++;
            } else if (s2Count[s2.charAt(l) - 'A'] + 1 == s1Count[s2.charAt(l) - 'A']) {
                match--;
            }

            l++;
        }

        return match == 26;
    }
}