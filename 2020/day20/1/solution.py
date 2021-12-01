from collections import defaultdict
class Tile:
    def __init__(self):
        self.borders=[]
        self.left = None
        self.right = None
        self.top = None
        self.bot = None

with open('../input.txt','rt') as f:
    tiles = dict()
    lines = f.readlines()
    i=0
    while i < len(lines):
        tile = int(lines[i][:-2].split(' ')[1])
        tiles[tile]=[]
        i+=1
        while i<len(lines) and lines[i]!='\n':
            line = lines[i].strip()
            tiles[tile].append(line)
            i+=1
        i+=1
        t = Tile()
        t.borders.append([ch for ch in tiles[tile][0]]) # top
        t.borders.append([ch for ch in tiles[tile][-1]]) # bot
        t.borders.append([tile[0] for tile in tiles[tile]]) #left
        t.borders.append([tile[-1] for tile in tiles[tile]]) #right
        tiles[tile]=t
        
    possible_neigbours = defaultdict(int)

    for tile1 in tiles:
        for tile2 in tiles:
            if tile1==tile2:
                continue
            ok = False
            for border1 in tiles[tile1].borders:
                for border2 in tiles[tile2].borders:
                    if border1==border2 or list(reversed(border1))==border2:
                        ok = True
                        break
                if ok:
                    break
            if ok:
                possible_neigbours[tile1]+=1
    res = 1
    cnt = 0
    for tile in possible_neigbours:
        if possible_neigbours[tile]==2:
            cnt+=1
            print(tile)
            res*=tile
    print(cnt,res)