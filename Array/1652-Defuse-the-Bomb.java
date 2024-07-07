class Solution {
    public int[] decrypt(int[] code, int k) {
        int[] list = new int[code.length];
        if (k == 0) {
            return list;
        }
        int start = 1;
        int end = k;

        if (k < 0) {
            k = -k;
            start = code.length - k;
            end = code.length - 1;
        }
        int sum = 0;
        for (int i = start; i < k; i++) {
            sum += code[i];
        }

        for (int i = 0; i < code.length; i++) {
            list[i] = sum;
            end++;
            sum = sum + code[end % code.length] - code[start % code.length];
            start++;
        }

        return list;
    }
}