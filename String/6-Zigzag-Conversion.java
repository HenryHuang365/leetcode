// The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

// P   A   H   N
// A P L S I I G
// Y   I   R
// And then read line by line: "PAHNAPLSIIGYIR"

// Write the code that will take a string and make this conversion given a number of rows:

// string convert(string s, int numRows);
 

// Example 1:

// Input: s = "PAYPALISHIRING", numRows = 3
// Output: "PAHNAPLSIIGYIR"
// Example 2:

// Input: s = "PAYPALISHIRING", numRows = 4
// Output: "PINALSIGYAHRPI"
// Explanation:
// P     I    N
// A   L S  I G
// Y A   H R
// P     I
// Example 3:

// Input: s = "A", numRows = 1
// Output: "A"
 

// Constraints:

// 1 <= s.length <= 1000
// s consists of English letters (lower-case and upper-case), ',' and '.'.
// 1 <= numRows <= 1000

// For Row 1 and Row n: each char is at s[(numRows - 1) * 2]
// For rows in the middle, each intermediate char is at s[i + (numRows - 1) * 2 - 2*r]

class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;
        String res = "";

        for (int r = 0; r < numRows; r++) {
            int increment = 2 * (numRows - 1);
            for (int i = r; i < s.length(); i += increment) {
                res += s.charAt(i);
                if (r > 0 && r < numRows - 1 && i + increment - 2*r < s.length()) {
                    res += s.charAt(i + increment - 2*r);
                }
            }
        }
        return res;
    }
}