inp = [6,19,0,5,7,13,1]
# inp = [0,3,6]
spoken = [x for x in inp]
history = dict()

for i,x in enumerate(inp):
    history[x]=[i+1]

turn = len(inp)+1
last = inp[-1]
while turn<=2020:
    print(last)
    if last not in history or len(history[last])<=1:
        history[0].append(turn)
        last=0
    else:
        num = history[last][-1]-history[last][-2]   
        if num not in history:
            history[num]=[]
        history[num].append(turn)
        last=num
    turn+=1
print(last)