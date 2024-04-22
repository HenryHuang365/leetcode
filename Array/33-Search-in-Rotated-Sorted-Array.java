class Solution {
    public int search(int[] nums, int target) {
        // Find the smallest num in nums
        int n = nums.length;
        int minIdx = findSmallest(nums);
        if (nums[minIdx] == target) {
            return minIdx;
        }
        if (nums[n - 1] == target) {
            return n - 1;
        }
        int l = (nums[n - 1] > target) ? minIdx : 0;
        int r = (nums[n - 1] > target) ? n - 1 : minIdx;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public int findSmallest(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > nums[r]) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}