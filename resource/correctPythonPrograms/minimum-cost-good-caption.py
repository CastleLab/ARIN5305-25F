class Solution:
    def minCostGoodCaption(self, caption: str) -> str:
        n = len(caption)

        if n < 3:
            return ""

        # Find the median character of substring, and compute the required operation
        @cache
        def get_cost(substring: str):
            substring = sorted(list(substring))
            l = len(substring)
            mc =substring[(l - 1) // 2]
            target = ord(mc)
            cost = 0
            for c in substring:
                if c == mc:
                    continue
                cost += abs(ord(c) - target)
            return cost, mc * l
        
        # dp[i][0] is the minimun operations for caption[:i]
        # dp[i][1] is the caption after minimun operations
        # NOTE "!" can be any string, since it should not appear in the final answer
        dp = [(float("inf"), "!")] * (n + 1)
        dp[0] = None

        # Initial dp with base cases
        for i in range(3, min(6, n + 1)):
            dp[i] = get_cost(caption[:i])
        
        # Start Dynamic Programing
        for i in range(6, n + 1):
            dp[i] = (float("inf"), "!")
            
            # Compare the three possible partitions
            for j in range(3, 6):
                cost, s = get_cost(caption[i - j:i])
                pre_cost, pre_s = dp[i-j]
                compare = cost + pre_cost
                
                if dp[i][0] > compare:
                    dp[i] = (compare, pre_s + s)
                elif dp[i][0] == compare:
                    dp[i] = (dp[i][0], min(dp[i][1], pre_s + s))

            # For saving memory, otherwise it will MLE
            dp[i-5] = None
            
        return dp[-1][1]
