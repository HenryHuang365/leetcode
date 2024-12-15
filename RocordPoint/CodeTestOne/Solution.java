// import java.util.Arrays;
// import java.util.*;
// import java.io.*;
public class Solution {

    public int MathChallenge(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int n = digits.length;

        // Step 1: Find the first digit that is smaller than the digit next to it, from right to left
        int i = n - 2;
        while (i >= 0 && digits[i] >= digits[i + 1]) {
            i--;
        }

        // If no such digit is found, that means the number has no greater permutation
        if (i == -1) {
            return -1;
        }

        // Step 2: Find the smallest digit on the right side of i that is greater than digits[i]
        int j = n - 1;
        while (digits[j] <= digits[i]) {
            j--;
        }

        // Step 3: Swap digits[i] and digits[j]
        swap(digits, i, j);

        // Step 4: Reverse the digits after position i
        reverse(digits, i + 1, n - 1);

        // Convert the modified char array back to an integer
        return Integer.parseInt(new String(digits));
    }

    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static void reverse(char[] arr, int start, int end) {
        while (start < end) {
            swap(arr, start, end);
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.MathChallenge(123));
    }
}

