from collections import defaultdict
from collections import deque
with open('../input.txt','rt') as f:
    g = defaultdict(set)
    for line in f:
        tokens = line.split()
        cur = tokens[0]+' '+tokens[1]
        curInd = 5
        while curInd<len(tokens):
            nxt = tokens[curInd]+' '+tokens[curInd+1]
            g[nxt].add(cur)
            curInd+=4
    print(g)
    res = set()
    q = deque()
    q.append('shiny gold')
    res.add('shiny gold')
    while q:
        nxt = q.popleft()
        for x in g[nxt]:
            if x not in res:
                q.append(x)
                res.add(x)

    print(len(res)-1)
