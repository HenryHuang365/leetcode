function findMaxAverage(nums: number[], k: number): number {
    let sumArray: number = 0;

    for (let i = 0; i < nums.length; i++) {
        sumArray += nums[i];
    }

    let maxValue = 0;

    for (let i = k; i < nums.length; i++) {
        sumArray = sumArray + nums[i] - nums[i - k];
        maxValue = Math.max(maxValue, sumArray);
    }

    return maxValue / k;
};