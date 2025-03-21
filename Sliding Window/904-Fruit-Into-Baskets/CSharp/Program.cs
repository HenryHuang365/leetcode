class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();

        int[] fruits = { 3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4 };
        Console.WriteLine("Output: " + program.TotalFruit(fruits));
    }

    public int TotalFruit(int[] fruits)
    {
        int res = 0;
        int left = 0;

        Dictionary<int, int> counts = new Dictionary<int, int>();

        for (int right = 0; right < fruits.Length; right++)
        {
            if (counts.TryGetValue(fruits[right], out int value))
            {
                counts[fruits[right]] = value + 1;
            }
            else
            {
                counts.Add(fruits[right], 1);
            }
            while (counts.Keys.Count > 2 && left < right)
            {
                counts[fruits[left]]--;
                if (counts[fruits[left]] == 0)
                {
                    counts.Remove(fruits[left]);
                }
                left++;
            }
            res = Math.Max(res, right - left + 1);
        }
        return res;
    }
}