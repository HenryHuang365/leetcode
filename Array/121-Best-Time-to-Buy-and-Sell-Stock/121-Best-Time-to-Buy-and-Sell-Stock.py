def maxProfit(prices):
        """
        :type prices: List[int]
        :rtype: int
        """
        res = 0
        l = 0
        r = l + 1
        
        while (r < len(prices)):
            profit = prices[r] - prices[l]
            if profit > 0:
                res = max(res, profit)
            else:
                l = r
        
            r += 1
            
        return res
        
        