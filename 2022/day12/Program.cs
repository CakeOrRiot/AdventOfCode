using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using static System.Int32;

const string filePath = "../../../input.txt";

int Solve1()
{
    var input = File.ReadLines(filePath).ToList();


    var grid = new List<List<char>>();
    var d = new Dictionary<(int, int), int>();
    var start = (0, 0);
    var end = (0, 0);
    for (var i = 0; i < input.Count; i++)
    {
        grid.Add(new List<char>(input[i]));

        var indS = input[i].IndexOf('S');
        if (indS != -1)
        {
            start = (i, indS);
            grid[i][indS] = 'a';
        }

        var indE = input[i].IndexOf('E');
        if (indE != -1)
        {
            end = (i, indE);
            grid[i][indE] = 'z';
        }
    }

    var q = new Queue<(int, int)>();
    q.Enqueue(start);
    var used = new HashSet<(int, int)>();
    d[start] = 0;
    used.Add(start);
    while (q.Count > 0)
    {
        var cur = q.Dequeue();
        var directions = new[]
        {
            (1, 0), (-1, 0), (0, 1), (0, -1)
        };
        foreach (var (dx, dy) in directions)
        {
            var next = (cur.Item1 + dx, cur.Item2 + dy);
            if (next.Item1 >= 0 && next.Item2 >= 0 && next.Item1 < grid.Count && next.Item2 < grid[0].Count &&
                !used.Contains(next) && grid[next.Item1][next.Item2] <= grid[cur.Item1][cur.Item2] + 1)
            {
                used.Add(next);
                q.Enqueue(next);
                d[next] = d[cur] + 1;
            }
        }
    }

    return d[end];
}


int Solve2()
{
    var input = File.ReadLines(filePath).ToList();


    var grid = new List<List<char>>();
    var d = new Dictionary<(int, int), int>();
    var start = (0, 0);
    var end = (0, 0);
    for (var i = 0; i < input.Count; i++)
    {
        grid.Add(new List<char>(input[i]));

        var indS = input[i].IndexOf('S');
        if (indS != -1)
        {
            start = (i, indS);
            grid[i][indS] = 'a';
        }

        var indE = input[i].IndexOf('E');
        if (indE != -1)
        {
            end = (i, indE);
            grid[i][indE] = 'z';
        }
    }

    var q = new Queue<(int, int)>();
    q.Enqueue(end);
    var used = new HashSet<(int, int)>();
    d[end] = 0;
    used.Add(end);
    while (q.Count > 0)
    {
        var cur = q.Dequeue();
        var directions = new[]
        {
            (1, 0), (-1, 0), (0, 1), (0, -1)
        };
        foreach (var (dx, dy) in directions)
        {
            var next = (cur.Item1 + dx, cur.Item2 + dy);
            if (next.Item1 >= 0 && next.Item2 >= 0 && next.Item1 < grid.Count && next.Item2 < grid[0].Count &&
                !used.Contains(next) && grid[next.Item1][next.Item2] >= grid[cur.Item1][cur.Item2]-1)
            {
                used.Add(next);
                q.Enqueue(next);
                d[next] = d[cur] + 1;
            }
        }
    }

    var res = MaxValue;
    for (var i = 0; i < grid.Count; i++)
    {
        for (var j = 0; j < grid[0].Count; j++)
        {
            if (grid[i][j] == 'a' && d.ContainsKey((i, j)))
                res = Math.Min(d[(i, j)], res);
        }
    }

    return res;
}

Console.WriteLine(Solve1());
Console.WriteLine(Solve2());