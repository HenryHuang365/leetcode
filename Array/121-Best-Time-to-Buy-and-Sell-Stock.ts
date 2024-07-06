function maxProfit(prices: number[]): number {
    let maxValue = 0;
    let l = 0;
    let r = l + 1;

    while (r < prices.length) {
        const value = prices[r] - prices[l];
        if (value > 0) {
            maxValue = Math.max(maxValue, value);
        } else {
            l = r;
        }

        r++;
    }

    return maxValue;
};