class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] nums = { 1, 2, 3, 1, 2, 3 };
        Console.WriteLine("Output: " + program.ContainsNearbyDuplicate(nums, 2));
    }

    public bool ContainsNearbyDuplicate(int[] nums, int k)
    {
        int left = 0;
        HashSet<int> set = new HashSet<int>();

        for (int right = 0; right < nums.Length; right++)
        {
            while (right - left > k)
            {
                set.Remove(nums[left]);
                left++;
            }

            if (set.Contains(nums[right]))
            {
                return true;
            }
            else
            {
                set.Add(nums[right]);
            }
        }

        return false;
    }
}