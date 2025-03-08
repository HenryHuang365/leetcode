public class OverlappingSquares {

    public static String calculateOverlap(String[] coordinates) {
        int numSquares = coordinates.length;
        int sideLength = numSquares;  // Side length is equal to the number of squares

        // Initialize boundaries of the overlapping region
        int left = Integer.MIN_VALUE;
        int right = Integer.MAX_VALUE;
        int top = Integer.MIN_VALUE;
        int bottom = Integer.MAX_VALUE;

        for (String coord : coordinates) {
            // Parse each coordinate pair
            String[] parts = coord.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            // Update the boundaries of the overlap
            left = Math.max(left, x);
            right = Math.min(right, x + sideLength);
            top = Math.max(top, y);
            bottom = Math.min(bottom, y + sideLength);
        }

        // Calculate the width and height of the overlapping region
        int overlapWidth = right - left;
        int overlapHeight = bottom - top;

        // If there is no overlap, the width or height will be non-positive
        if (overlapWidth <= 0 || overlapHeight <= 0) {
            return "0";  // Return "0" as a string
        }

        // Calculate the area of the overlapping region
        int overlapArea = overlapWidth * overlapHeight;

        // Return the area as a string
        return Integer.toString(overlapArea);
    }

    public static void main(String[] args) {
        String[] coordinates = {"4,5", "5,3", "3,3"};
        // Calculate and print the overlapping area
        String result = calculateOverlap(coordinates);
        System.out.println(result);
    }
}
