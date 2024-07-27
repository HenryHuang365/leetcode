function minSubArrayLen(target: number, nums: number[]): number {
    let l = 0;
    let r = 0;
    let sum = 0;
    let minLen = Number.MAX_VALUE;
    while (r < nums.length) {
        if (sum < target) {
            sum += nums[r];
            r++;
        }

        while (sum >= target) {
            minLen = Math.min(minLen, r - l);
            sum -= nums[l];
            l++;
        }
    }
    return minLen === Number.MAX_VALUE ? 0 : minLen;
};