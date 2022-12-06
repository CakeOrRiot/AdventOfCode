using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

const string FILE_PATH = "../../../input.txt";

int solve_1()
{
    var q = new Queue<char>();
    var input = File.ReadLines(FILE_PATH).ToList()[0];
    var i = 0;
    var count = 4;
    foreach (var symbol in input)
    {
        if (q.Count == count)
        {
            if (q.ToHashSet().Count == count)
                return i;
            q.Dequeue();
        }
        q.Enqueue(symbol);
        i++;
    }

    throw new Exception("No answer");
}

int solve_2()
{
    var q = new Queue<char>();
    var input = File.ReadLines(FILE_PATH).ToList()[0];
    var i = 0;
    var count = 14;
    foreach (var symbol in input)
    {
        if (q.Count == count)
        {
            if (q.ToHashSet().Count == count)
                return i;
            q.Dequeue();
        }
        q.Enqueue(symbol);
        i++;
    }

    throw new Exception("No answer");
}

Console.WriteLine(solve_1());
Console.WriteLine(solve_2());