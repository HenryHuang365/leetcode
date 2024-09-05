/**
 * Solution
 */

import java.util.Map;
import java.util.HashMap;
class Solution {
    public boolean isUnique(String s) {
        Map<Character, Integer> counts = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            counts.put(s.charAt(i), counts.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (int value : counts.values()) {
            if (value > 1) return false;
        }
        return true;
    }

    public boolean isUniqueASCII(String s) {
        if (s.length() > 128) {
            return false;
        }

        boolean[] charUsed = new boolean[128];

        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i);
            if (charUsed[index]) {
                return false;
            }

            charUsed[index] = true;
        }

        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.isUnique("asd0s"));
    }
}