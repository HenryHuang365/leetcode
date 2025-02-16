import java.util.List;
import java.util.ArrayList;

class Solution {
    public List<Integer> grayCode(int n) {
        List<Integer> result = new ArrayList<>();
        int size = 1 << n; // 2^n
        for (int i = 0; i < size; i++) {
            result.add(i ^ (i >> 1));
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println("grayCode: " + solution.grayCode(2).toString());
    }
}