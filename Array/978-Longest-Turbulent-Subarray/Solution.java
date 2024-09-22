class Solution {
    public int maxTurbulenceSize(int[] arr) {
        if (arr.length == 1) {
            return 1;
        }
        int res = 0;
        int inc = 1;
        int dec = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                dec = inc + 1;
                inc = 1;
            } else if (arr[i] > arr[i - 1]) {
                inc = dec + 1;
                dec = 1;
            } else {
                inc = 1;
                dec = 1;
            }
            res = Math.max(res, Math.max(dec, inc));
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr = new int[] { 9, 4, 2, 10, 7, 8, 8, 1, 9 };
        System.out.println("Output: " + solution.maxTurbulenceSize(arr));
    }
}