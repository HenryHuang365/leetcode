
/*
395-Longest-Substring-with-At-Least-K-Repeating-Characters

Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.

if no such substring exists, return 0.

 

Example 1:

Input: s = "aaabb", k = 3
Output: 3
Explanation: The longest substring is "aaa", as 'a' is repeated 3 times.
Example 2:

Input: s = "ababbc", k = 2
Output: 5
Explanation: The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
*/
import java.util.Map;
import java.util.HashMap;

class LeastKCharacters {
    public int longestSubstring(String s, int k) {
        return helper(s, k, 0, s.length());
    }

    public int helper(String s, int k, int start, int end) {
        if (end - start < k)
            return 0;
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = start; i < end; i++) {
            counts.put(s.charAt(i), counts.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (int i = start; i < end; i++) {
            if (counts.get(s.charAt(i)) < k) {
                int leftLength = helper(s, k, start, i);
                int rightLength = helper(s, k, i + 1, end);
                return Math.max(leftLength, rightLength);
            }
        }

        return end - start;
    }

    public static void main(String[] args) {
        LeastKCharacters leastKCharacters = new LeastKCharacters();

        System.out.println("Output: " + leastKCharacters.longestSubstring("bbaaacbd", 3));
        System.out.println("Output: " + leastKCharacters.longestSubstring("ababbc", 2));
    }
}
