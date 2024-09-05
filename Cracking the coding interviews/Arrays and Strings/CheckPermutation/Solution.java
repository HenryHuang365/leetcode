import java.util.Arrays;

/**
 * Solution
 */
class Solution {
    public boolean isPermutation(String strOne, String strTwo) {
        if (strOne.length() != strTwo.length()) return false;
        int n = strOne.length();
        int[] strOneCounts = new int[128];
        int[] strTwoCounts = new int[128];
        for (int i = 0; i < n; i++) {
            int indexOne = strOne.charAt(i);
            int indexTwo = strTwo.charAt(i);
            strOneCounts[indexOne]++;
            strTwoCounts[indexTwo]++;
        }
        return Arrays.equals(strOneCounts, strTwoCounts);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println("Output: " + solution.isPermutation("abcd", "addb"));
    }
}