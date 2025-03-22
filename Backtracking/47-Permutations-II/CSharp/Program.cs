class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] nums = { 1, 2, 3 };
        IList<IList<int>> list = program.PermuteUnique(nums);
        foreach (var path in list)
        {
            foreach (var num in path)
            {
                Console.Write(num + " ");
            }

            Console.WriteLine("");
        }
    }

    public IList<IList<int>> PermuteUnique(int[] nums)
    {
        Array.Sort(nums);
        IList<IList<int>> list = new List<IList<int>>();
        List<int> path = new List<int>();
        bool[] used = new bool[nums.Length];

        Backtracking(nums, list, path, used);
        return list;
    }

    public void Backtracking(int[] nums, IList<IList<int>> list, List<int> path, bool[] used)
    {
        if (path.Count == nums.Length)
        {
            list.Add(new List<int>(path));
        }
        else
        {
            for (int i = 0; i < nums.Length; i++)
            {
                if (used[i] || (i > 0 && !used[i - 1] && nums[i - 1] == nums[i])) continue;

                path.Add(nums[i]);
                used[i] = true;
                Backtracking(nums, list, path, used);
                path.RemoveAt(path.Count - 1);
                used[i] = false;
            }
        }
    }
}