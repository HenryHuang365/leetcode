public class Solution {

    public int MathChallenge(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        int n = digits.length;
        if (n == 1)
            return -1;
        int swapPoint = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (digits[i] < digits[i + 1]) {
                swap(digits, i, i + 1);
                swapPoint = i + 1;
                break;
            }
        }
        if (swapPoint == -1)
            return -1;
        for (int i = swapPoint; i < n - 1; i++) {
            if (digits[i] > digits[i + 1]) {
                swap(digits, i, i + 1);
            }
        }

        return Integer.parseInt(new String(digits));
    }

    public void swap(char[] digits, int i, int j) {
        Character temp = digits[j];
        digits[j] = digits[i];
        digits[i] = temp;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("Output: " + solution.MathChallenge(41352));
    }
}
