class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] nums = { 1, 2, 3 };
        IList<IList<int>> list = program.Permute(nums);
        foreach (var path in list)
        {
            foreach (var num in path)
            {
                Console.Write(num + " ");
            }
            Console.WriteLine("");
        }
    }

    public IList<IList<int>> Permute(int[] nums)
    {
        IList<IList<int>> list = new List<IList<int>>();
        List<int> path = new List<int>();

        Backtracking(nums, list, path);
        return list;
    }

    public void Backtracking(int[] nums, IList<IList<int>> list, List<int> path)
    {
        if (path.Count == nums.Length)
        {
            list.Add(new List<int>(path));
        }
        else
        {
            for (int i = 0; i < nums.Length; i++)
            {
                if (path.Contains(nums[i])) continue;

                path.Add(nums[i]);
                Backtracking(nums, list, path);
                path.RemoveAt(path.Count - 1);
            }
        }
    }
}