function topKFrequent(nums: number[], k: number): number[] {
    // This is how I can initialise an array of integer array with fixed size in ts.
    const bucket: number[][] = Array.from({ length: nums.length + 1 }, () => []);
    let frequencyMap: Map<number, number> = new Map();

    for (const num of nums) {
        frequencyMap.set(num, (frequencyMap.get(num) || 0) + 1);
    }

    for (const [key, frequency] of frequencyMap) {
        bucket[frequency].push(key);
    }

    let res: number[] = [];
    for (let i = bucket.length - 1; i >= 0 && res.length < k; i--) {
        if (bucket[i].length > 0) {
            res.push(...bucket[i]);
        }
    }
    return res;
};