// You are given an array prices where prices[i] is the price of a given stock on the ith day.

// You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

// Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

// Example 1:

// Input: prices = [7,1,5,3,6,4]
// Output: 5
// Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
// Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
// Example 2:

// Input: prices = [7,6,4,3,1]
// Output: 0
// Explanation: In this case, no transactions are done and the max profit = 0.

// Constraints:

// 1 <= prices.length <= 105
// 0 <= prices[i] <= 104


// This is an Easy Greedy Algo question

class Solution {
    public int maxProfit(int[] prices) {
        int maxValue = 0;
        int l = 0;
        int r = l + 1;

        while (r < prices.length) {
            int value = prices[r] - prices[l];
            // Important: value > 0 is the key indicator for moving left pointer. 
            if (value > 0) {
                maxValue = Math.max(maxValue, value);                
            } else {
                // Sometimes, sliding windows does only increment the pointer by one or using while. 
                // we can simply just assign the right pointer to the left pointer. 
                l = r;
            }
            // The right pointer should always move in every loop. 
            r++;
        }

        return maxValue;
    }
}