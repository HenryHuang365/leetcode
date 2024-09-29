import org.junit.Test;
import static org.junit.Assert.*;

public class SolutionTest {

    @Test
    public void testExample1() {
        Solution solution = new Solution();
        int[][] grid = {
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 0, 0, 0, 1, 1 },
                { 0, 0, 0, 1, 1 }
        };
        assertEquals(1, solution.numDistinctIslands(grid));
    }

    @Test
    public void testExample2() {
        Solution solution = new Solution();
        int[][] grid = {
                { 1, 1, 0, 1, 1 },
                { 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1 },
                { 1, 1, 0, 1, 1 }
        };
        assertEquals(3, solution.numDistinctIslands(grid));
    }

    @Test
    public void testEmptyGrid() {
        Solution solution = new Solution();
        int[][] grid = {
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 0, 0, 0 }
        };
        assertEquals(0, solution.numDistinctIslands(grid));
    }

    @Test
    public void testFullLandGrid() {
        Solution solution = new Solution();
        int[][] grid = {
                { 1, 1, 1, 1 },
                { 1, 1, 1, 1 },
                { 1, 1, 1, 1 },
                { 1, 1, 1, 1 }
        };
        assertEquals(1, solution.numDistinctIslands(grid));
    }

    @Test
    public void testSingleCellIsland() {
        Solution solution = new Solution();
        int[][] grid = {
                { 1 }
        };
        assertEquals(1, solution.numDistinctIslands(grid));
    }

    @Test
    public void testSingleRowIsland() {
        Solution solution = new Solution();
        int[][] grid = {
                { 1, 1, 0, 0 },
                { 0, 0, 0, 0 },
                { 0, 1, 1, 1 },
                { 0, 0, 0, 0 }
        };
        assertEquals(2, solution.numDistinctIslands(grid));
    }

    @Test
    public void testDifferentShapes() {
        Solution solution = new Solution();
        int[][] grid = {
                { 1, 0, 0, 1, 1 },
                { 1, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 1 },
                { 0, 1, 0, 0, 1 }
        };
        assertEquals(3, solution.numDistinctIslands(grid));
    }

    @Test
    public void testLargeGrid() {
        Solution solution = new Solution();
        int[][] grid = new int[50][50];
        grid[0][0] = 1;
        grid[0][1] = 1;
        grid[49][48] = 1;
        grid[49][49] = 1;
        assertEquals(1, solution.numDistinctIslands(grid));
    }
}
