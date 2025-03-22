class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        int[] candidates = { 2, 5, 2, 1, 2 };
        IList<IList<int>> list = program.CombinationSum2(candidates, 5);

        foreach (var path in list)
        {
            foreach (var num in path)
            {
                Console.Write(num + " ");
            }
            Console.WriteLine("");
        }

    }

    public IList<IList<int>> CombinationSum2(int[] candidates, int target)
    {
        Array.Sort(candidates);
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
                if (i > start && i > 0 && candidates[i] == candidates[i - 1])
                {
                    continue;
                }

                path.Add(candidates[i]);
                Backtracking(candidates, list, path, sum + candidates[i], target, i + 1);
                path.RemoveAt(path.Count - 1);
            }
        }
    }
}