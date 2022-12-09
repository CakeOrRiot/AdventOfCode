using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

const string filePath = "../../../input.txt";

int solve_1()
{
    var input = File.ReadLines(filePath);
    var (x, y) = (0, 0);
    var (tailX, tailY) = (0, 0);
    var tailPositions = new HashSet<(int, int)>();
    foreach (var line in input)
    {
        var tokens = line.Split(' ');
        var direction = tokens[0];
        var step = int.Parse(tokens[1]);
        Func<int, int, int, int, bool> isClose = (x, y, tailX, tailY) =>
            Math.Abs(x - tailX) <= 1 && Math.Abs(y - tailY) <= 1;


        for (var i = 0; i < step; i++)
        {
            switch (direction)
            {
                case "U":
                    y += 1;
                    break;
                case "D":
                    y -= 1;
                    break;
                case "L":
                    x -= 1;
                    break;
                case "R":
                    x += 1;
                    break;
                default: throw new Exception("??");
            }

            Func<int, int> sign = x =>
            {
                return x switch
                {
                    > 0 => 1,
                    < 0 => -1,
                    _ => 0
                };
            };
            if (!isClose(x, y, tailX, tailY))
            {
                tailX += sign(x - tailX);
                tailY += sign(y - tailY);
            }

            tailPositions.Add((tailX, tailY));
        }
    }


    return tailPositions.Count;
}


int solve_2()
{
    var input = File.ReadLines(filePath);
    var knots = new Point[11];
    for (var i = 0; i < knots.Length; i++)
        knots[i] = new Point(0, 0);
    var tailPositions = new HashSet<(int, int)>();
    foreach (var line in input)
    {
        var tokens = line.Split(' ');
        var direction = tokens[0];
        var step = int.Parse(tokens[1]);
        Func<int, int, int, int, bool> isClose = (x, y, tailX, tailY) =>
            Math.Abs(x - tailX) <= 1 && Math.Abs(y - tailY) <= 1;


        for (var i = 0; i < step; i++)
        {
            switch (direction)
            {
                case "U":
                    knots[0].Y += 1;
                    break;
                case "D":
                    knots[0].Y -= 1;
                    break;
                case "L":
                    knots[0].X -= 1;
                    break;
                case "R":
                    knots[0].X += 1;
                    break;
                default: throw new Exception("??");
            }

            Func<int, int> sign = x =>
            {
                return x switch
                {
                    > 0 => 1,
                    < 0 => -1,
                    _ => 0
                };
            };

            for (var knot_ind = 0; knot_ind < knots.Length - 1; knot_ind++)
            {
                var (x, y) = (knots[knot_ind].X, knots[knot_ind].Y);
                var (tailX, tailY) = (knots[knot_ind + 1].X, knots[knot_ind + 1].Y);
                if (!isClose(x, y, tailX, tailY))
                {
                    tailX += sign(x - tailX);
                    tailY += sign(y - tailY);
                }

                if (knot_ind == 8)
                    tailPositions.Add((tailX, tailY));

                knots[knot_ind].X = x;
                knots[knot_ind].Y = y;

                knots[knot_ind + 1].X = tailX;
                knots[knot_ind + 1].Y = tailY;
            }
        }
    }


    return tailPositions.Count;
}

Console.WriteLine(solve_1());
Console.WriteLine(solve_2());

public record Point(int X, int Y)
{
    public int X { get; set; } = X;
    public int Y { get; set; } = Y;
}