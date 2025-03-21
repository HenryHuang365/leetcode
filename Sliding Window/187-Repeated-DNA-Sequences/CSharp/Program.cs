class Program
{
    public static void Main(string[] args)
    {
        Program program = new Program();
        IList<string> result = program.FindRepeatedDnaSequences("AAAAAAAAAAA");
        IList<string> resultTwo = program.FindRepeatedDnaSequencesTwo("AAAAAAAAAAA");
        foreach (string s in result)
        {
            Console.Write(s + " ");
        }
        Console.WriteLine("");
        foreach (string s in resultTwo)
        {
            Console.Write(s + " ");
        }
    }

    public IList<string> FindRepeatedDnaSequences(string s)
    {
        IList<string> result = new List<string>();
        Dictionary<string, int> dict = new Dictionary<string, int>();

        for (int i = 0; i <= s.Length - 10; i++)
        {
            string sequence = s.Substring(i, 10);
            if (dict.TryGetValue(sequence, out int value))
            {
                dict[sequence] = value + 1;
            }
            else
            {
                dict.Add(sequence, 1);
            }

        }

        foreach (KeyValuePair<string, int> kvp in dict)
        {
            if (kvp.Value > 1)
            {
                result.Add(kvp.Key);
            }
        }
        return result;
    }

    public IList<string> FindRepeatedDnaSequencesTwo(string s)
    {
        IList<string> result = new List<string>();
        HashSet<string> set = new HashSet<string>();

        for (int i = 0; i <= s.Length - 10; i++)
        {
            string sequence = s.Substring(i, 10);
            if (set.Contains(sequence))
            {
                if (!result.Contains(sequence))
                {
                    result.Add(sequence);
                }
            }
            else
            {
                set.Add(sequence);
            }
        }
        return result;
    }
}