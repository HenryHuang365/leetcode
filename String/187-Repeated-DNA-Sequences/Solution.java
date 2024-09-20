
/**
 * 187. Repeated DNA Sequences
 * 
 * The DNA sequence is composed of a series of nucleotides abbreviated as 'A',
 * 'C', 'G', and 'T'.
 * For example, "ACGAATTCCG" is a DNA sequence.
 * When studying DNA, it is useful to identify repeated sequences within the
 * DNA.
 * Given a string s that represents a DNA sequence, return all the
 * 10-letter-long sequences (substrings) that occur more than once in a DNA
 * molecule. You may return the answer in any order.
 * 
 * Example 1:
 * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
 * Output: ["AAAAACCCCC","CCCCCAAAAA"]
 * 
 * Example 2:
 * Input: s = "AAAAAAAAAAAAA"
 * Output: ["AAAAAAAAAA"]
 */

import java.util.*;

public class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if (s.length() <= 10)
            return res;
        
        Map<String, Integer> counts = new HashMap<>();
        for (int i = 0; i <= s.length() - 10; i++) {
            String subString = s.substring(i, i + 10);
            counts.put(subString, counts.getOrDefault(subString, 0) + 1);
        }
        for (String subString : counts.keySet()) {
            if (counts.get(subString) > 1) {
                res.add(subString);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(
                "Output: " + solution.findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT").toString());
    }
}
