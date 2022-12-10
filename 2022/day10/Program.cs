using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Intrinsics.X86;

const string filePath = "../../../input.txt";

int solve_1()
{
    var input = File.ReadLines(filePath);
    var cycle = 1;
    var cyclesToComlete = new Dictionary<string, int>() { { "noop", 1 }, { "addx", 2 } };
    var x = 1;
    var res = 0;

    Action updateRes = () =>
    {
        if ((cycle - 20) % 40 == 0)
        {
            res += cycle * x;
        }
    };

    foreach (var line in input)
    {
        var tokens = line.Split(' ');
        var command = tokens[0];

        for (var i = 0; i < cyclesToComlete[command]; i++)
        {
            updateRes();
            cycle++;
        }

        if (command == "addx")
        {
            x += int.Parse(tokens[1]);
        }
    }

    return res;
}


void solve_2()
{
    var input = File.ReadLines(filePath);
    var cycle = 1;
    var cyclesToComlete = new Dictionary<string, int>() { { "noop", 1 }, { "addx", 2 } };
    var x = 1;

    var grid = new char[6, 40];
    for (var i = 0; i < grid.GetLength(0); i++)
    for (var j = 0; j < grid.GetLength(1); j++)
        grid[i, j] = '.';

    foreach (var line in input)
    {
        var tokens = line.Split(' ');
        var command = tokens[0];

        for (var i = 0; i < cyclesToComlete[command]; i++)
        {
            var crtPos = cycle - 1;
            if (crtPos % 40 >= x - 1 && crtPos % 40 <= x + 1)
                grid[crtPos / 40, crtPos % 40] = '#';
            cycle++;
        }

        if (command == "addx")
        {
            x += int.Parse(tokens[1]);
        }
    }

    for (var i = 0; i < grid.GetLength(0); i++)
    {
        for (var j = 0; j < grid.GetLength(1); j++)
            Console.Write(grid[i, j]);
        Console.WriteLine();
    }
}

Console.WriteLine(solve_1());
solve_2();
