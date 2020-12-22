from collections import deque
with open('../input.txt','rt') as f:
    lines = f.readlines()
    i=1
    cards1 = deque()
    while lines[i]!='\n':
        cards1.append(int(lines[i]))
        i+=1
    i+=2
    cards2 = deque()
    while i<len(lines):
        cards2.append(int(lines[i]))
        i+=1


    while cards1 and cards2:
        top1 = cards1.popleft()
        top2 = cards2.popleft()
        if top1>top2:
            cards1.append(top1)
            cards1.append(top2)
        elif top1<top2:
            cards2.append(top2)
            cards2.append(top1)
        else:
            raise Exception
    
    if cards2:
        cards1=cards2
    l=len(cards1)
    res = 0
    for i,card in enumerate(cards1):
        res+=card*(l-i)
    print(res)