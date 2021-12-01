from collections import defaultdict
with open('../input.txt','rt') as f:
    memory = defaultdict(int)
    lines = list(map(lambda x : x.rstrip(),f.readlines()))
    cur = 0
    while cur<len(lines):
        mask = lines[cur][7:]
        cur+=1
        while cur<len(lines) and lines[cur][1]=='e':
            open_ind = lines[cur].index('[')
            close_ind = lines[cur].index(']')
            adrress = int(lines[cur][open_ind+1:close_ind])
            val = int(lines[cur][lines[cur].index('=')+2:])
            val_bin = "{0:b}".format(val).zfill(36)
            apply_mask = []
            for x,y in zip(mask,val_bin):
                if x!='X':
                    apply_mask.append(x)
                else:
                    apply_mask.append(y)
            apply_mask=''.join(apply_mask)
            memory[adrress] = int(apply_mask,2)
            cur+=1
    res = 0
    for x in memory:
        res+=int(memory[x])
    print(res)