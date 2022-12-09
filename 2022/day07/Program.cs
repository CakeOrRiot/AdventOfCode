using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

const string filePath = "../../../input.txt";

Node parseTree()
{
    var input = File.ReadLines(filePath).ToList();
    var root = new Node("/", null);
    var curNode = root;
    var folderToNode = new Dictionary<string, Node>();

    for (var i = 0; i < input.Count; i++)
    {
        var line = input[i];
        if (line[0] == '$')
        {
            var tokens = line.Split(' ');
            var command = tokens[1];
            if (command == "ls")
            {
                i++;
                while (i < input.Count && input[i][0] != '$')
                {
                    var fileTokens = input[i].Split(' ');
                    if (fileTokens[0] == "dir")
                    {
                        var name = fileTokens[1];
                        curNode.Children.Add(new Node(name, curNode));
                    }
                    else
                    {
                        var size = long.Parse(fileTokens[0]);
                        var name = fileTokens[1];
                        curNode.Files.Add(new MyFile(size, name));
                        curNode.Size += size;
                    }

                    i++;
                }

                i--;
            }
            else // cd
            {
                var folder = tokens[2];
                if (folder == "..")
                {
                    if (curNode.Parent != null)
                        curNode = curNode.Parent;
                }
                else if (folder == "/")
                {
                    curNode = root;
                }
                else
                {
                    var node = curNode.Children.Find(x => x.Name == folder);
                    if (node == null)
                    {
                        node = new Node(folder, curNode);
                        curNode.Children.Add(node);
                    }

                    curNode = node;
                }
            }
        }
    }

    GetSizes(root);


    return root;
}

const long threshold = 100_000;

long solve_1(Node node)
{
    long result = 0;
    if (node.Size <= threshold)
    {
        result += node.Size;
    }

    result += node.Children.Sum(solve_1);

    return result;
}


Node solve_2(Node node, long minSize)
{
    var candidates = node.Children.Where(x => x.Size >= minSize).OrderBy(x => x.Size); //.FirstOrDefault();
    var best = candidates.FirstOrDefault();
    foreach (var candidate in candidates)
    {
        var a = solve_2(candidate, minSize);
        if (best == null)
            best = a;
        if (a == null)
            continue;
        if (a.Size < best.Size)
            best = a;
    }

    return best;
}

var root = parseTree();
Console.WriteLine(solve_1(root));
var minSize = 30_000_000 - (70_000_000 - root.Size);
Console.WriteLine(solve_2(root, minSize).Size);

static long GetSizes(Node node)
{
    foreach (var child in node.Children)
    {
        node.Size += GetSizes(child);
    }

    return node.Size;
}


class Node
{
    public string Name { get; set; }
    public List<Node> Children { get; set; }
    public Node Parent { get; set; }
    public List<MyFile> Files { get; set; }

    public long Size { get; set; }

    public Node(string name, Node parent)
    {
        Name = name;
        Children = new List<Node>();
        Parent = parent;
        Files = new List<MyFile>();
    }
};

public record MyFile(long size, string name);