using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

const string filePath = "../../../input.txt";

int solve_1()
{
    var input = File.ReadLines(filePath).ToList();
    var matrix = input.Select(line => line.Select(x => x - '0').ToList()).ToList();

    var res = 0;
    for (var i = 0; i < matrix.Count; i++)
    {
        for (var j = 0; j < matrix[0].Count; j++)
        {
            var edge = i == 0 || j == 0 || i == matrix.Count - 1 || j == matrix[0].Count - 1;
            if (edge)
                res++;
            else
            {
                var directions = new List<(int, int)> { (0, -1), (0, 1), (-1, 0), (1, 0) };
                var good = directions.Select(arg =>
                {
                    var (dx, dy) = arg;
                    var x = i + dx;
                    var y = j + dy;
                    while (x >= 0 && y >= 0 && x < matrix.Count && y < matrix[0].Count)
                    {
                        if (matrix[x][y] >= matrix[i][j])
                            return false;
                        x += dx;
                        y += dy;
                    }

                    return true;
                }).Any(x => x);

                if (good)
                {
                    res++;
                }
            }
        }
    }

    return res;
}

int solve_2()
{
    var input = File.ReadLines(filePath).ToList();
    var matrix = input.Select(line => line.Select(x => x - '0').ToList()).ToList();

    var res = 0;
    for (var i = 0; i < matrix.Count; i++)
    {
        for (var j = 0; j < matrix[0].Count; j++)
        {
            var directions = new List<(int, int)> { (0, -1), (0, 1), (-1, 0), (1, 0) };
            var view = directions.Select(arg =>
            {
                var (dx, dy) = arg;
                var dist = 0;
                var x = i + dx;
                var y = j + dy;
                while (x >= 0 && y >= 0 && x < matrix.Count && y < matrix[0].Count)
                {
                    if (matrix[x][y] >= matrix[i][j])
                    {
                        dist++;
                        break;
                    }

                    dist++;
                    x += dx;
                    y += dy;
                }

                return dist;
            }).Aggregate(1, (x, y) => x * y);

            res = Math.Max(res, view);
        }
    }

    return res;
}


Console.WriteLine(solve_1());
Console.WriteLine(solve_2());