using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace LinkedListOperationCSharp.CleanEmpty
{
    public class CleanEmpty
    {
        public static void cleanEmptyFunc()
        {
            var json = "{\"name\":{\"first\":\"Robert\",\"middle\":\"\",\"last\":\"Smith\"},\"age\":25,\"DOB\":\"-\",\"hobbies\":[\"running\",\"coding\",\"-\"],\"education\":{\"highschool\":\"N\\/A\",\"college\":\"Yale\"}}";

            var result = CleanJson(json);
            Console.WriteLine(JsonConvert.SerializeObject(result));
        }


        private static Dictionary<string, object> CleanJson(string json)
        {
            var data = JsonConvert.DeserializeObject<Dictionary<string, object>>(json);
            var exclusions = new HashSet<string> { "", "-", "N/A", "n/a" };
            Dictionary<string, object> resultDictionary = [];

            foreach (var item in data!)
            {
                var key = item.Key;
                var value = item.Value;
                switch (value)
                {
                    case long _:
                        resultDictionary.Add(key, value);
                        break;

                    case string str:
                        if (!exclusions.Contains(str))
                            resultDictionary.Add(key, str);
                        break;

                    case JArray jArray:
                        var arrResult = new List<string>();
                        foreach (var itemobj in jArray)
                        {
                            var itemStr = (string)itemobj!;
                            if (!exclusions.Contains(itemStr))
                            {
                                arrResult.Add(itemStr);
                            }
                        }
                        resultDictionary.Add(key, arrResult);
                        break;

                    default:
                        var json2 = JsonConvert.SerializeObject(value);
                        var result = CleanJson(json2);
                        resultDictionary.Add(key, result);
                        break;
                }
            }
            return resultDictionary;
        }

    }
}
