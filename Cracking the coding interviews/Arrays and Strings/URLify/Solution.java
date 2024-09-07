/**
 * URLify
 * 
 * Write a method to replace all spaces in a string with '%20'. You may assume that the string 
 * has sufficient space at the end to hold the additional characters, and that you are given the "true"
 * length of the string. (Note: If implementing in Java, please use a character array so that you can
 * perform this operation in place.)
 * EXAMPLE
 * Input: "Mr John Smith ", 13
 * Output: "Mr%20John%20Smith"
 * 
 * Here the "You may assume that the string 
 * has sufficient space at the end to hold the additional characters" is something needs to confirm with interviewers. 
 * 
 * Does "sufficient" means exactly sufficient or more than sufficient? 
 */

import java.util.Arrays;
public class Solution {
    public void replaceSpace(char[] str, int trueLength) {
        int spaceCounts = 0;
        for (int i = 0; i < trueLength; i++) {
            if (str[i] == ' ') spaceCounts++;
        }

        int index = trueLength + 2 * spaceCounts;
        if (trueLength < str.length) str[trueLength] = '\0'; // End array for C/C++

        for (int i = trueLength - 1; i >=0; i--) {
            if (str[i] == ' ') {
                str[index - 1] = '0';
                str[index - 2] = '2';
                str[index - 3] = '%';
                index = index - 3;
            } else {
                str[index - 1] = str[i];
                index--;
            }
        }

        System.out.println("Output: " + Arrays.toString(str));
        System.out.println("String: " + new String(str));
    }
    
    public static void main(String[] args) {
        Solution solution = new Solution();
        char[] input = new char[]{'M', 'r', ' ', 'J', 'o', 'h', 'n', ' ', 'S', 'm', 'i', 't', 'h', ' ', ' ', ' ', ' '};
        System.out.println("Output: " + Arrays.toString(input));
        solution.replaceSpace(input, 13);
    }
}
