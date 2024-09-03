import java.util.Map;
import java.util.HashMap;
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int max = 0;
        Map<Character, Integer> counts = new HashMap<>();
        int left = 0;
        for (int right = 0; right < s.length(); right++) {
            Character currChar = s.charAt(right);
            counts.put(currChar, counts.getOrDefault(currChar, 0) + 1);
            while (counts.size() > k) {
                Character leftChar = s.charAt(left);
                counts.put(leftChar, counts.get(leftChar) - 1);
                if (counts.get(leftChar) == 0) counts.remove(leftChar);
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.lengthOfLongestSubstringKDistinct("aaaaaaaab", 2));
    }
}
