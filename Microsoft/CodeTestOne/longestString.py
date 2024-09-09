class Solution:
    def solution(self, AA: int, AB: int, BB: int) -> str:
        # Initialization
        count_AA = AA
        count_BB = BB
        count_AB = AB
        result = ""

        # If both AA and BB are zero, only use AB strings
        if count_AA == 0 and count_BB == 0:
            for _ in range(count_AB):
                result += "AB"
            return result

        # If count_AA is greater than count_BB
        if count_AA > count_BB:
            for _ in range(count_BB):
                result += "AA"
                result += "BB"

            for _ in range(count_AB):
                result += "AB"

            result += "AA"

        # If count_AA is equal to count_BB
        elif count_AA == count_BB:
            for _ in range(count_BB):
                result += "AA"
                result += "BB"

            for _ in range(count_AB):
                result += "AB"

        # If count_BB is greater than count_AA
        else:
            for _ in range(count_AA):
                result += "BB"
                result += "AA"

            result += "BB"
            
            for _ in range(count_AB):
                result += "AB"

        return result



# Test cases
if __name__ == "__main__":
    solution = Solution()

    # Test case 1: AA = 5, AB = 0, BB = 2
    print("Test case 1:")
    print("Result:", solution.solution(5, 0, 2))  # Expected: "AABBAABBAA"

    # Test case 2: AA = 1, AB = 2, BB = 1
    print("\nTest case 2:")
    print("Result:", solution.solution(1, 2, 1))  # Expected: "BBABABAA" or another valid result

    # Test case 3: AA = 0, AB = 2, BB = 0
    print("\nTest case 3:")
    print("Result:", solution.solution(0, 2, 0))  # Expected: "ABAB"

    # Test case 4: AA = 0, AB = 0, BB = 10
    print("\nTest case 4:")
    print("Result:", solution.solution(0, 0, 10))  # Expected: "BB"

    # Test case 5: AA = 3, AB = 3, BB = 3
    print("\nTest case 5:")
    print("Result:", solution.solution(1, 1, 10))  # Should be valid, no "AAA" or "BBB"
