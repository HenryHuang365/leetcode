class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        Console.WriteLine(program.LengthOfLongestSubstring("bbbbb"));
    }

    public int LengthOfLongestSubstring(string s)
    {
        int res = 0;
        int left = 0;
        int right = 0;
        HashSet<char> set = new HashSet<char>();

        while (right < s.Length)
        {
            char c = s[right];
            if (!set.Contains(c))
            {
                set.Add(c);
                right++;
            }
            else
            {
                while (set.Contains(c) && left <= right)
                {
                    set.Remove(s[left]);
                    left++;
                }
            }

            res = Math.Max(res, right - left);
        }

        return res;
    }
}