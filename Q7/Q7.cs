using System;
using System.Collections.Generic;
using System.IO;

class Question_Seven
{
    static bool canReach(long target, int next, char op, long sum, List<long> values)
    {
        if (next == values.Count)
        {
            return sum == target;
        }

        if (sum > target)
        {
            return false;
        }

        if (op == '+')
        {
            sum += values[next];
        }
        else
        {
            sum *= values[next];
        }

        return canReach(target, next + 1, '+', sum, values) || canReach(target, next + 1, '*', sum, values);
    }

    static bool canReachAgain(long target, int next, char op, long sum, List<long> values)
    {
        if (next == values.Count)
        {
            return sum == target;
        }

        if (canReachAgain(target, next + 1, '+', sum + values[next], values))
        {
            return true;
        }

        if (canReachAgain(target, next + 1, '*', sum * values[next], values))
        {
            return true;
        }

        string concatenated = sum.ToString() + values[next].ToString();
        if (long.TryParse(concatenated, out long concatenatedValue) &&
            canReachAgain(target, next + 1, '|', concatenatedValue, values))
        {
            return true;
        }

        return false;
    }

    static long Part1(List<Tuple<long, List<long>>> data)
    {
        long sum = 0;
        foreach (var entry in data)
        {
            long key = entry.Item1;
            var values = entry.Item2;
            if (canReach(key, 1, '+', values[0], values) || canReach(key, 1, '*', values[0], values))
            {
                sum += key;
            }
        }
        return sum;
    }

    static long Part2(List<Tuple<long, List<long>>> data)
    {
        long sum = 0;
        foreach (var entry in data)
        {
            long key = entry.Item1;
            var values = entry.Item2;

            if (canReach(key, 1, '+', values[0], values) || canReach(key, 1, '*', values[0], values))
            {
                sum += key;
            }
            else
            {
                if (canReachAgain(key, 1, '|', values[0], values))
                {
                    sum += key;
                }
            }
        }
        return sum;
    }

    static void Main(string[] args)
    {
        string filePath = "input.txt";
        var dataList = ReadFileToArray(filePath);

        Console.WriteLine("Part 1 Output: " + Part1(dataList));

        Console.WriteLine("Part 2 Output: " + Part2(dataList));
    }

    static List<Tuple<long, List<long>>> ReadFileToArray(string filePath)
    {
        var dataList = new List<Tuple<long, List<long>>>();

        foreach (var line in File.ReadLines(filePath))
        {
            var parts = line.Split(':');
            long key = long.Parse(parts[0].Trim());
            var values = new List<long>();
            
            foreach (var value in parts[1].Trim().Split(' '))
            {
                if (long.TryParse(value, out long num))
                {
                    values.Add(num);
                }
            }

            dataList.Add(new Tuple<long, List<long>>(key, values));
        }

        return dataList;
    }
}
