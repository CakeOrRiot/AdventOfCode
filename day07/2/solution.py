from collections import defaultdict
from collections import deque
with open('../input.txt','rt') as f:
    g = defaultdict(set)
    for line in f:
        tokens = line.split()
        cur = tokens[0]+' '+tokens[1]
        curInd = 5
        while curInd<len(tokens):
            if tokens[curInd-1]=='no':
                break
            nxt =int(tokens[curInd-1]), tokens[curInd]+' '+tokens[curInd+1]
            g[cur].add(nxt)
            curInd+=4
    print(g)

    def count(bag):
        used = set()
        res = 0
        q = deque()
        q.append(bag)
        used.add(bag)
        for x in g[bag]:
            res+=x[0]*(1+count(x[1]))
        return res
    print(count('shiny gold'))
