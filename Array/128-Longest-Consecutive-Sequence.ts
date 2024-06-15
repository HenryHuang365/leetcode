function longestConsecutive(nums: number[]): number {
    let longestSequence = 0;
    const numSet = new Set(nums);

    console.log("numSet: ", numSet);

    for (let num of nums) {
        if (!numSet.has(num - 1)) {
            let length = 0;
            while (numSet.has(num++)) {
                length++;
            }

            longestSequence = Math.max(longestSequence, length);
        }
    }
    return longestSequence;
};