import org.junit.Test;
import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testBasicCase() {
        Solution solution = new Solution();
        int[] nums = { 1, 0, 1, 1, 0, 1 };
        int expected = 4;
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testAllOnes() {
        Solution solution = new Solution();
        int[] nums = { 1, 1, 1, 1, 1 };
        int expected = 5;
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testAllZeros() {
        Solution solution = new Solution();
        int[] nums = { 0, 0, 0, 0 };
        int expected = 1; // We can flip one 0 to make a single 1
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testOneElementZero() {
        Solution solution = new Solution();
        int[] nums = { 0 };
        int expected = 1; // We can flip the only 0
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testOneElementOne() {
        Solution solution = new Solution();
        int[] nums = { 1 };
        int expected = 1;
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testMultipleFlipsNeeded() {
        Solution solution = new Solution();
        int[] nums = { 1, 0, 1, 0, 1, 0, 1 };
        int expected = 3; // Only one flip allowed, so max consecutive ones = 3
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testEmptyArray() {
        Solution solution = new Solution();
        int[] nums = {};
        int expected = 0; // Empty array, no 1s at all
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testConsecutiveZeros() {
        Solution solution = new Solution();
        int[] nums = { 0, 0, 0, 0, 0 };
        int expected = 1; // We can flip one zero to get a single 1
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }

    @Test
    public void testConsecutiveOnesWithSingleZero() {
        Solution solution = new Solution();
        int[] nums = { 1, 1, 1, 0, 1, 1, 1 };
        int expected = 7; // Flip the zero in the middle, max consecutive ones = 7
        assertEquals(expected, solution.findMaxConsecutiveOnes(nums));
    }
}