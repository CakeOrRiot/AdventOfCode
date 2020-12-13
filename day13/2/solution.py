def egcd(a, b):
    if a == 0:
        return (b, 0, 1)
    else:
        g, y, x = egcd(b % a, a)
        return (g, x - (b // a) * y, y)

def modinv(a, m):
    g, x, y = egcd(a, m)
    if g != 1:
        raise Exception('modular inverse does not exist')
    else:
        return x % m

with open('../input.txt','rt') as f:
    f.readline()
    line = f.readline().rstrip()
    ids = []
    M = 1
    for i,x in enumerate(line.split(',')):
        if x!='x':
            ids.append((i,int(x)))
            M*=int(x)
    print(ids)
    ms = [M//bus_id[1] for bus_id in ids]
    ms_inv = [modinv(mi,bus_id[1]) for mi,bus_id in zip(ms,ids)]
    t = 0
    for i in range(len(ids)):
        t+=(ids[i][1]-ids[i][0])*ms[i]*ms_inv[i]
    print(t%M)