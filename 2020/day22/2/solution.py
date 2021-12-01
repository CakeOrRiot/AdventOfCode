from collections import deque
from copy import deepcopy

def game(cards1, cards2,prev_games):
    # print(cards1)
    # print(cards2)
    # print(prev_games)
    # print('--------------------------------------------')
    while cards1 and cards2:
        if (tuple(cards1),tuple(cards2)) in prev_games:
            return True
        prev_games.append((tuple(deepcopy(cards1)),tuple(deepcopy(cards2))))
        
        top1 = cards1.popleft()
        top2 = cards2.popleft()
        # print(top1,top2)
        if len(cards1)>=top1 and len(cards2)>=top2:
            if game(deque(list(cards1)[:top1]),deque(list(cards2)[:top2]),[]):
                cards1.append(top1)
                cards1.append(top2)
            else:
                cards2.append(top2)
                cards2.append(top1)
        else:
            if top1>top2:
                cards1.append(top1)
                cards1.append(top2)
            elif top1<top2:
                cards2.append(top2)
                cards2.append(top1)
            else:
                raise Exception
        
    if not cards2:
        return True
    if not cards1:
        return False
    

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

    res = game(cards1,cards2,[])
    if not res:
        cards1=cards2
    l=len(cards1)
    res = 0
    for i,card in enumerate(cards1):
        res+=card*(l-i)
    
    print(res)