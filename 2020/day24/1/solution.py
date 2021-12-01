from collections import defaultdict
with open('../input.txt','rt') as f:
    points=defaultdict(int)
    for line in f:
        line = line.strip()
        cur = 0
        directions = []
        
        while cur<len(line):
            if line[cur]=='s' or line[cur]=='n':
                directions.append(line[cur]+line[cur+1])
                cur+=1
            elif line[cur]=='e' or line[cur]=='w':
                directions.append(line[cur])
            else:
                raise Exception("INPUT ERROR")

            cur+=1
        resultingP = [0,0,0]
        for direction in directions:
            if direction=='se':
                resultingP[0]-=1
                resultingP[2]+=1
            if direction=='sw':
                resultingP[1]-=1
                resultingP[2]+=1
            if direction=='ne':
                resultingP[1]+=1
                resultingP[2]-=1
            if direction=='nw':
                resultingP[0]+=1
                resultingP[2]-=1
            if direction=='e':
                resultingP[0]-=1
                resultingP[1]+=1
            if direction=='w':
                resultingP[0]+=1
                resultingP[1]-=1
        points[tuple(resultingP)]+=1
    print(points)
    res = 0
    for p in points:
        if points[p]%2!=0:
            res+=1
    print(res)