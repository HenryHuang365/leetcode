function containsNearbyDuplicate(nums: number[], k: number): boolean {
    let windowSet: Set<number> = new Set();
    let l = 0;

    for (let r = 0; r < nums.length; r++) {
        if (Math.abs(l - r) > k) {
            windowSet.delete(nums[l]);
            l++;
        }

        if (windowSet.has(nums[r])) {
            return true;
        }

        windowSet.add(nums[r]);
    }

    return false;
};