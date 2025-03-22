class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] candidates = { 2 };
        IList<IList<int>> list = program.CombinationSum(candidates, 1);
        foreach (var items in list)
        {
            foreach (var item in items)
            {
                Console.Write(item + " ");
            }
            Console.WriteLine("");
        }
    }

    public IList<IList<int>> CombinationSum(int[] candidates, int target)
    {
        IList<IList<int>> list = new List<IList<int>>();
        IList<int> path = new List<int>();
        Backtracking(candidates, list, path, 0, target, 0);
        return list;
    }

    public void Backtracking(int[] candidates, IList<IList<int>> list, IList<int> path, int sum, int target, int start)
    {
        if (sum > target)
        {
            return;
        }
        else if (sum == target)
        {
            list.Add(new List<int>(path));
        }
        else
        {
            for (int i = start; i < candidates.Length; i++)
            {
                path.Add(candidates[i]);
                Backtracking(candidates, list, path, sum + candidates[i], target, i);
                path.RemoveAt(path.Count - 1);
            }
        }
    }
}