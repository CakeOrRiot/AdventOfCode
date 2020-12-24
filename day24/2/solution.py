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
    black_tiles = set()
    for p in points:
        if points[p]%2!=0:
            black_tiles.add(p)

    def get_neighbours(tile):
        offsets = [(-1,1,0),(1,-1,0),(0,1,-1),(1,0,-1),(-1,0,1),(0,-1,1)]
        n = []
        for offset in offsets:
            n.append((tile[0]+offset[0],tile[1]+offset[1],tile[2]+offset[2]))
        return n

    def get_black(tile):
        cnt_black=0
        for neighbour in get_neighbours(tile):
            if neighbour in black_tiles:
                cnt_black+=1
        return cnt_black

    def shoud_update(tile,cnt):
        if tile in black_tiles and (cnt==0 or cnt>2):
            return True
        if tile not in black_tiles and cnt==2:
            return True
        return False
    
    for move in range(100):
        to_update = set()

        for tile in black_tiles:
            cnt_black = get_black(tile)
            if shoud_update(tile,cnt_black):
                to_update.add(tile)
            for neighbour in get_neighbours(tile):
                if shoud_update(neighbour,get_black(neighbour)):
                    to_update.add(neighbour)
        # print(to_update)
        for upd in to_update:
            if upd in black_tiles:
                black_tiles.remove(upd)
            else:
                black_tiles.add(upd)

    print(len(black_tiles))

