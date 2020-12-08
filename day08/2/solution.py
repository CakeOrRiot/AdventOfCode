def try_to_eval(lines):
    acc = 0
    used_lines = set()
    cur = 0
    while cur<len(lines):
        if cur < 0:
            return False,acc
        if cur in used_lines:
            return False,acc
            
        used_lines.add(cur)
        if lines[cur][:3]=='acc':
            acc+=int(lines[cur][3:])
            cur+=1
        elif lines[cur][:3]=='nop':
            cur+=1
        elif lines[cur][:3]=='jmp':
            cur+=int(lines[cur][3:])
    if cur!=len(lines):
        return False,acc
    return True,acc


with open('../input.txt','rt') as f:
    lines = f.readlines()
    for i in range(len(lines)):
        saved = lines[i]
        if lines[i][:3]=='jmp':
            lines[i] ='nop' + lines[i][3:]
            val = try_to_eval(lines)
            if val[0] == True:
                print(i,val[1])
            lines[i]=saved
        
        elif lines[i][:3]=='nop':
            lines[i]='jmp' + lines[i][3:]
            val = try_to_eval(lines)
            if val[0] == True:
                print(i,val[1])
            lines[i]=saved