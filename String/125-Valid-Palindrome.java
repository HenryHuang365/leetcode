// A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.

// Given a string s, return true if it is a palindrome, or false otherwise.

// Example 1:

// Input: s = "A man, a plan, a canal: Panama"
// Output: true
// Explanation: "amanaplanacanalpanama" is a palindrome.
// Example 2:

// Input: s = "race a car"
// Output: false
// Explanation: "raceacar" is not a palindrome.
// Example 3:

// Input: s = " "
// Output: true
// Explanation: s is an empty string "" after removing non-alphanumeric characters.
// Since an empty string reads the same forward and backward, it is a palindrome.

// Constraints:

// 1 <= s.length <= 2 * 105
// s consists only of printable ASCII characters.

class Solution {
    public boolean isPalindrome(String s) {
        String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        if (str.length() == 0) {
            return true;
        }
        String reversed_str = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed_str += str.charAt(i);
        }
        if (str.equals(reversed_str.toLowerCase())) {
            return true;
        }
        return false;
    }

    public boolean isPalindrome1(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            while (left < right && !alphaNum(s.charAt(left))) {
                left += 1;
            }

            while (left < right && !alphaNum(s.charAt(right))) {
                right -= 1;
            }

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left += 1;
            right -= 1;
        }

        return true;
    }

    public boolean alphaNum(Character c) {
        return (((int) 'A' <= (int) c && (int) c <= (int) 'Z') || ((int) 'a' <= (int) c && (int) c <= (int) 'z')
                || ((int) '0' <= (int) c && (int) c <= (int) '9'));
    }
}