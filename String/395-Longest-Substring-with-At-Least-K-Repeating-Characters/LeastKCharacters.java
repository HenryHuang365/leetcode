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
        return longestSubstringHelper(s, 0, s.length(), k);
    }

    private int longestSubstringHelper(String s, int start, int end, int k) {
        if (end - start < k) {
            return 0; // 子串长度小于 k 不可能有符合条件的子串
        }

        // 统计当前子串的字符频率
        Map<Character, Integer> counts = new HashMap<>();
        for (int i = start; i < end; i++) {
            counts.put(s.charAt(i), counts.getOrDefault(s.charAt(i), 0) + 1);
        }

        // 找到第一个频率小于 k 的字符
        for (int i = start; i < end; i++) {
            if (counts.get(s.charAt(i)) < k) {
                // 以该字符为分割点，递归地计算两边的最大长度
                int leftPart = longestSubstringHelper(s, start, i, k);
                int rightPart = longestSubstringHelper(s, i + 1, end, k);
                return Math.max(leftPart, rightPart);
            }
        }

        // 如果所有字符的频率都满足要求，则直接返回当前子串长度
        return end - start;
    }

    public static void main(String[] args) {
        LeastKCharacters leastKCharacters = new LeastKCharacters();

        System.out.println("Output: " + leastKCharacters.longestSubstring("aaabb", 3));
        System.out.println("Output: " + leastKCharacters.longestSubstring("ababbc", 2));
    }
}
