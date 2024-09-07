/**
 * Solution
 * 
 * Note: how can you remove all the spaces from a string in Java.
 */

import java.util.Map;
import java.util.HashMap;
public class Solution {

    public boolean isPalindromePermutation(String s) {
        StringBuilder result = new StringBuilder();
        for (char letter : s.toCharArray()) {
            if (letter != ' ') {
                result.append(letter);
            }
        }

        String newString = result.toString().toLowerCase();        
        Map<Character, Integer> counts = new HashMap<>();
        int n = newString.length();
        for (int i = 0; i < n; i++) {
            counts.put(newString.charAt(i), counts.getOrDefault(newString.charAt(i), 0) + 1);
        }

        if (n % 2 == 0) {
            for (int value : counts.values()) {
                if (value % 2 != 0) return false;
            }
        } else {
            int oddValue = 0;
            for (int value : counts.values()) {
                if (value % 2 != 0) {
                    oddValue++;
                }

                if (oddValue > 1) return false;
            }
        }


        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.isPalindromePermutation("Tact Coa"));
    }
}