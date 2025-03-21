class Program
{
    public static void Main(String[] args)
    {
        int[] height = { 1, 1 };

        Program program = new Program();
        Console.WriteLine(program.MaxArea(height));
    }

    public int MaxArea(int[] height)
    {
        int maxArea = 0;
        int left = 0;
        int right = height.Length - 1;

        while (right > left)
        {
            int h = Math.Min(height[right], height[left]);
            int w = right - left;

            maxArea = Math.Max(maxArea, h * w);

            if (height[left] <= height[right])
            {
                left++;
            }
            else
            {
                right--;
            }
        }
        return maxArea;
    }
}