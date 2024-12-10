// Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

// A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

// Example 1:

// Input: digits = "23"
// Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
// Example 2:

// Input: digits = ""
// Output: []
// Example 3:

// Input: digits = "2"
// Output: ["a","b","c"]

// Constraints:

// 0 <= digits.length <= 4
// digits[i] is a digit in the range ['2', '9'].

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits.isEmpty()) return res;
        StringBuilder path = new StringBuilder();

        Map<Character, String[]> map = new HashMap<>();
        map.put('2', new String[] {"a", "b", "c"});
        map.put('3', new String[] {"d", "e", "f"});
        map.put('4', new String[] {"g", "h", "i"});
        map.put('5', new String[] {"j", "k", "l"});
        map.put('6', new String[] {"m", "n", "o"});
        map.put('7', new String[] {"p", "q", "r", "s"});
        map.put('8', new String[] {"t", "u", "v"});
        map.put('9', new String[] {"w", "x", "y", "z"});

        backtrack(res, digits, path, map, 0);
        return res;
    }

    public void backtrack(List<String> res, String digits, StringBuilder path, Map<Character, String[]> map, int start) {
        if (path.toString().length() == digits.length()) {
            res.add(path.toString());
        } else {
            for (int i = start; i < digits.length(); i++) {
                String[] mapString = map.get(digits.charAt(i));
                for (String s : mapString) {
                    path.append(s);
                    backtrack(res, digits, path, map, i + 1);
                    path.delete(path.length() - 1, path.length());
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.letterCombinations("2"));
    }
}