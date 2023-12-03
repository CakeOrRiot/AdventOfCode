import re

matr = []
with open("input.txt") as f:
    for i, line in enumerate(f.readlines()):
        matr.append(line.strip())
res = 0
for i, line in enumerate(matr):
    iter_gear = re.finditer("\*", line)

    for x in iter_gear:
        adj = []
        pos = x.span()[0]
        iters = []
        if i - 1 >= 0:
            iters.append(re.finditer("\d+", matr[i - 1]))
        iters.append(re.finditer("\d+", line))
        if i + 1 < len(matr):
            iters.append(re.finditer("\d+", matr[i + 1]))

        for iter_ in iters:
            for y in iter_:
                if (y.span()[0] <= pos and y.span()[1] >= pos) or (
                    y.span()[0] - 1 == pos
                ):
                    adj.append(int(y.group(0)))

        if len(adj) == 2:
            res += adj[0] * adj[1]

print(res)
