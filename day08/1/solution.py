with open('../input.txt','rt') as f:
    acc = 0
    used_lines = set()
    lines = f.readlines()
    cur = 0
    while True:
        if cur in used_lines:
            print(acc)
            break
        used_lines.add(cur)
        if lines[cur][:3]=='acc':
            acc+=int(lines[cur][3:])
            cur+=1
        if lines[cur][:3]=='nop':
            cur+=1
        if lines[cur][:3]=='jmp':
            cur+=int(lines[cur][3:])
        