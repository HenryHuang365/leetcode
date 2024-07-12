function findLHS(nums: number[]): number {
    let subLength = 0;
    let count: Map<number, number> = new Map();

    for (let i = 0; i < nums.length; i++) {
        count.set(nums[i], (count.get(nums[i]) || 0) + 1);
    }

    for (let key of count.keys()) {
        if (count.has(key + 1)) {
            const length = (count.get(key) || 0) + (count.get(key + 1) || 0);
            subLength = Math.max(subLength, length);
        }
    }

    return subLength;
};