class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] nums = { 0 };
        IList<IList<int>> list = program.Subsets(nums);

        foreach (var path in list)
        {
            foreach (var num in path)
            {
                Console.Write(num + " ");
            }
            Console.WriteLine("");
        }
    }

    public IList<IList<int>> Subsets(int[] nums)
    {
        IList<IList<int>> list = new List<IList<int>>();
        List<int> path = new List<int>();

        Backtracking(nums, list, path, 0);
        return list;
    }

    public void Backtracking(int[] nums, IList<IList<int>> list, List<int> path, int start)
    {
        list.Add(new List<int>(path));
        for (int i = start; i < nums.Length; i++)
        {
            path.Add(nums[i]);
            Backtracking(nums, list, path, i + 1);
            path.RemoveAt(path.Count - 1);
        }
    }
}