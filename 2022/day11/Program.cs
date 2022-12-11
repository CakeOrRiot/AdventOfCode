using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Intrinsics.X86;

const string filePath = "../../../input.txt";

int Solve1()
{
    var input = File.ReadLines(filePath);
    var monkeys = new List<Monkey<int>>();
    foreach (var line in input.Chunk(7))
    {
        for (var i = 0; i < line.Length; i++)
            line[i] = line[i].Replace(",", string.Empty);
        var monkey = new Monkey<int>();

        monkey.Items = line[1].Split(' ', StringSplitOptions.RemoveEmptyEntries)[2..].Select(int.Parse).ToList();

        var tokens = line[2].Split(' ', StringSplitOptions.RemoveEmptyEntries)[4..];
        monkey.Transform = tokens[0] switch
        {
            "*" => x => x * (tokens[1] == "old" ? x : int.Parse(tokens[1])) / 3,
            "+" => x => (x + (tokens[1] == "old" ? x : int.Parse(tokens[1]))) / 3,
            _ => throw new Exception("???")
        };

        var testConstant = int.Parse(line[3].Split(' ', StringSplitOptions.RemoveEmptyEntries).Last());
        monkey.Test = x => x % testConstant == 0;
        var throwMonkeys = (int.Parse(line[4].Split(' ', StringSplitOptions.RemoveEmptyEntries).Last()),
            int.Parse(line[5].Split(' ', StringSplitOptions.RemoveEmptyEntries).Last()));
        monkey.Pass = throwMonkeys;
        monkeys.Add(monkey);
    }

    for (var round = 0; round < 20; round++)
    {
        foreach (var monkey in monkeys)
        {
            foreach (var item in monkey.Items)
            {
                var transformed = monkey.Transform(item);
                if (monkey.Test(transformed))
                {
                    monkeys[monkey.Pass.Item1].Items.Add(transformed);
                }
                else
                {
                    monkeys[monkey.Pass.Item2].Items.Add(transformed);
                }
            }

            monkey.ItemInspections += monkey.Items.Count;
            monkey.Items.Clear();
        }
    }

    return monkeys.Select(x => x.ItemInspections).OrderByDescending(x => x).Take(2).Aggregate(1, (x, y) => x * y);
}


ulong Solve2()
{
    var input = File.ReadLines(filePath);
    var monkeys = new List<Monkey<ulong>>();
    const ulong mod = 2 * 17 * 13 * 19 * 5 * 3 * 7 * 11;
    foreach (var line in input.Chunk(7))
    {
        for (var i = 0; i < line.Length; i++)
            line[i] = line[i].Replace(",", string.Empty);
        var monkey = new Monkey<ulong>();

        monkey.Items = line[1].Split(' ', StringSplitOptions.RemoveEmptyEntries)[2..].Select(ulong.Parse).ToList();

        var tokens = line[2].Split(' ', StringSplitOptions.RemoveEmptyEntries)[4..];
        monkey.Transform = tokens[0] switch
        {
            "*" => x => (x * (tokens[1] == "old" ? x : ulong.Parse(tokens[1]))) % mod,
            "+" => x => (x + (tokens[1] == "old" ? x : ulong.Parse(tokens[1]))) % mod,
            _ => throw new Exception("???")
        };

        var testConstant = ulong.Parse(line[3].Split(' ', StringSplitOptions.RemoveEmptyEntries).Last());
        monkey.Test = x => x % testConstant == 0;
        var throwMonkeys = (int.Parse(line[4].Split(' ', StringSplitOptions.RemoveEmptyEntries).Last()),
            int.Parse(line[5].Split(' ', StringSplitOptions.RemoveEmptyEntries).Last()));
        monkey.Pass = throwMonkeys;
        monkeys.Add(monkey);
    }

    for (var round = 0; round < 10000; round++)
    {
        foreach (var monkey in monkeys)
        {
            foreach (var item in monkey.Items)
            {
                try
                {
                    var transformed = monkey.Transform(item);
                    if (monkey.Test(transformed))
                    {
                        monkeys[monkey.Pass.Item1].Items.Add(transformed);
                    }
                    else
                    {
                        monkeys[monkey.Pass.Item2].Items.Add(transformed);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(item);
                }
            }

            monkey.ItemInspections += (ulong)monkey.Items.Count;
            monkey.Items.Clear();
        }
    }

    return monkeys.Select(x => x.ItemInspections).OrderByDescending(x => x).Take(2)
        .Aggregate(1UL, (x, y) => x * y);
}

Console.WriteLine(Solve1());
Console.WriteLine(Solve2());


internal class Monkey<T> where T : new()
{
    public Monkey(List<T> items, Func<T, T> transform, Func<T, bool> test, (int, int) pass)
    {
        Items = items;
        Transform = transform;
        Test = test;
        Pass = pass;
        ItemInspections = new T();
    }

    public List<T> Items { get; set; }
    public Func<T, T> Transform { get; set; }
    public Func<T, bool> Test { get; set; }
    public (int, int) Pass { get; set; }

    public T ItemInspections { get; set; }

    public Monkey()
    {
        ItemInspections = new T();
    }
}