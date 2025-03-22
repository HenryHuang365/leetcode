using System.Numerics;

class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] nums = { 1, 4, 4 };
        Console.WriteLine("Output: " + program.MinSubArrayLen(4, nums));
    }

    public int MinSubArrayLen(int target, int[] nums)
    {
        int left = 0;
        int sum = 0;
        int res = int.MaxValue;

        for (int right = 0; right < nums.Length; right++)
        {
            sum += nums[right];

            while (sum >= target)
            {
                res = Math.Min(res, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return res == int.MaxValue ? 0 : res;
    }
}