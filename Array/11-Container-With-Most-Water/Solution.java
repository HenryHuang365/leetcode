class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int water = 0;

        while (left < right) {
            int h = Math.min(height[left], height[right]);
            water = Math.max(water, (right - left) * h);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return water;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] height = new int[] {1,8,6,2,5,4,8,3,7};
        System.out.println("Output: " + solution.maxArea(height));
    }
}