
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
        int[] pCounts = new int[128];
        int n = p.length();
        for (int i = 0; i < n; i++) {
            int index = p.charAt(i) - 'a';
            pCounts[index]++;
        }
        for (int i = 0; i <= s.length() - n; i++) {
            int[] sCounts = new int[128];
            for (int j = i; j < i + n; j++) {
                int index = s.charAt(j) - 'a';
                sCounts[index]++;
            }
            if (Arrays.equals(sCounts, pCounts)) {
                res.add(i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.findAnagrams("cbaebabacd", "abc").toString());
    }
}