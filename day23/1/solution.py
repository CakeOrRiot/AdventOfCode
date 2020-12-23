cups = list(map(int,list('614752839')))
cups = list(map(int,list('389125467')))
cur = 0
n = len(cups)
print(n)
for _ in range(100):
    print(cups,cur)
    cur_cup = cups[cur]
    removed = [cups[(cur+1)%n],cups[(cur+2)%n],cups[(cur+3)%n]]
    for r in removed:
        cups.remove(r)
    
    dest = cur_cup-1
    if dest<=0:
        dest = n
    while dest in removed:
        dest-=1
        if dest<=0:
            dest = n

    # print(dest,removed)
    dest_ind = (cups.index(dest)+1)
    for r in removed:
        cups.insert(dest_ind,r)
        dest_ind+=1
        # dest_ind%=n+1
    
    cur = (cups.index(cur_cup) + 1) %n
    # print(cups,cur)
print(cups)

        

    