
/**
 * Solution
 * Given two strings s and p, return an array of all the start indices of p's
 * anagrams in s. You may return the answer in any order.
 * 
 * An Anagram is a word or phrase formed by rearranging the letters of a
 * different word or phrase, typically using all the original letters exactly
 * once.
 * 
 * Example 1:
 * Input: s = "cbaebabacd", p = "abc"
 * Output: [0,6]
 * 
 * Explanation:
 * The substring with start index = 0 is "cba", which is an anagram of "abc".
 * The substring with start index = 6 is "bac", which is an anagram of "abc".
 * 
 * Example 2:
 * Input: s = "abab", p = "ab"
 * Output: [0,1,2]
 * 
 * Explanation:
 * The substring with start index = 0 is "ab", which is an anagram of "ab".
 * The substring with start index = 1 is "ba", which is an anagram of "ab".
 * The substring with start index = 2 is "ab", which is an anagram of "ab".
 */

import java.util.*;

public class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int n = p.length();
        if (s.length() < n) {
            return res;
        }
        int[] pCounts = new int[128];
        for (int i = 0; i < n; i++) {
            pCounts[p.charAt(i) - 'a']++;
        }
        for (int i = 0; i <= s.length() - n; i++) {
            int[] sCounts = new int[128];
            for (int j = i; j < i + n; j++) {
                sCounts[s.charAt(j) - 'a']++;
            }
            if (Arrays.equals(sCounts, pCounts)) {
                res.add(i);
            }
        }
        return res;
    }

    // This is another method that uses less space complexity
    public List<Integer> findAnagramsWithLessSpaceComplexity(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int n = p.length();
        if (s.length() < n)
            return res;
        int[] pCounts = new int[26];
        int[] sCounts = new int[26];
        for (int i = 0; i < n; i++) {
            pCounts[p.charAt(i) - 'a']++;
            sCounts[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i <= s.length() - n; i++) {
            if (Arrays.equals(sCounts, pCounts))
                res.add(i);

            if (i + n < s.length()) {
                sCounts[s.charAt(i) - 'a']--;
                sCounts[s.charAt(i + n) - 'a']++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Method 1 output: " + solution.findAnagrams("cbaebabacd", "abc").toString());
        System.out.println("Method 2 output: " + solution.findAnagramsWithLessSpaceComplexity("cbaebabacd", "abc").toString());
    }
}
