from collections import defaultdict

def get_all_adresses(adr):
    for i,x in enumerate(adr):
        if x=='X':
            new_adr0 = adr[::]
            new_adr0[i]='0'
            new_adr1 = adr[::]
            new_adr1[i]='1'
            return get_all_adresses(new_adr0) + get_all_adresses(new_adr1)
            
    return [adr]

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
            adrress_bin = "{0:b}".format(adrress).zfill(36)
            apply_mask = []
            for x,y in zip(mask,adrress_bin):
                if x=='X':
                    apply_mask.append('X')
                elif x=='0':
                    apply_mask.append(y)
                else:
                    apply_mask.append('1')
            for adress in get_all_adresses(apply_mask):
                adr=int(''.join(adress),2)
                memory[adr] = int(val)
            cur+=1
    res = 0
    for x in memory:
        res+=int(memory[x])
    print(res)