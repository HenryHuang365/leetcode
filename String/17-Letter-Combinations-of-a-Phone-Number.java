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
        List<String> list = new ArrayList<>();
        if (digits.length() == 0 || digits == null) {
            return list;
        }

        Map<Character, String> digits_letter = new HashMap<>();

        digits_letter.put('2', "abc");
        digits_letter.put('3', "def");
        digits_letter.put('4', "ghi");
        digits_letter.put('5', "jkl");
        digits_letter.put('6', "mno");
        digits_letter.put('7', "pqrs");
        digits_letter.put('8', "tuv");
        digits_letter.put('9', "wxyz");

        backtrack(digits, 0, new StringBuilder(), list, digits_letter);
        return list;
    }

    public void backtrack(String digits, int idx, StringBuilder comb, List<String> list,
            Map<Character, String> digits_letter) {
        if (idx == digits.length()) {
            list.add(comb.toString());
            return;
        }

        String letters = digits_letter.get(digits.charAt(idx));

        for (char letter : letters.toCharArray()) {
            comb.append(letter);
            backtrack(digits, idx + 1, comb, list, digits_letter);
            comb.deleteCharAt(comb.length() - 1);
        }
    }
}