class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        Console.WriteLine("Output: " + program.LongestPalindrome("babad"));
    }

    public string LongestPalindrome(string s)
    {
        if (s.Length <= 1)
        {
            return s;
        }

        string oddRes = s.Substring(0, 1);

        for (int i = 0; i < s.Length; i++)
        {
            int left = i;
            int right = i;

            while (left >= 0 && right < s.Length && s[left] == s[right])
            {
                string p = s.Substring(left, right - left + 1);
                if (p.Length > oddRes.Length)
                {
                    oddRes = p;
                }

                left--;
                right++;
            }
        }

        string evenRes = s.Substring(0, 1);

        for (int k = 0; k < s.Length - 1; k++)
        {
            int l = k;
            int r = k + 1;

            while (l >= 0 && r < s.Length && s[l] == s[r])
            {
                string p = s.Substring(l, r - l + 1);
                if (p.Length > evenRes.Length)
                {
                    evenRes = p;
                }

                l--;
                r++;
            }
        }

        return oddRes.Length > evenRes.Length ? oddRes : evenRes;
    }
}