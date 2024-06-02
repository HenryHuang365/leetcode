function containsDuplicate(nums: number[]): boolean {
    let numSet: Set<number> = new Set();
    // A solution without sorting may be faster
    for (const num of nums) {
        if (numSet.has(num)) {
            return true;
        }
        numSet.add(num);
    }
    return false;
};

function containsDuplicate1(nums: number[]): boolean {
    const len = nums.length;
    const newNums = nums.sort();
    for (let i = 0; i < len; i++) {
        if (newNums[i] == nums[i+1]) {
            return true;
        }        
    }
    return false;
};