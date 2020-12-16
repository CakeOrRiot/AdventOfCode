with open('../input.txt','rt') as f:
    sections = {}
    lines = f.readlines()
    print(lines)
    for i,line in enumerate(lines):
        if line=='\n':
            break
        tokens = line.split(': ')
        section = tokens[0]
        sections[section]=[]
        
        tokens = tokens[1].split(' ')
        range_f = tokens[0].split('-')
        range_s = tokens[2].split('-')
        sections[section].append((int(range_f[0]),int(range_f[1])))
        sections[section].append((int(range_s[0]),int(range_s[1])))
    i+=5
    # print(sections.values())
    res=0
    while i<len(lines):
        # print(i)
        numbers = list(map(int,lines[i].split(',')))
        
        for num in numbers:
            # print(num)
            valid_for_any = False
            for restriction in sections.values():
                if restriction[0][0]<=num<=restriction[0][1] or restriction[1][0]<=num<=restriction[1][1]:
                    # print(f'{num} valid by {restriction}')
                    valid_for_any=True
                    break
            if not valid_for_any:
                # print(num)
                res+=num

        i+=1
    print(res)