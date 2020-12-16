from collections import defaultdict

with open('../input.txt','rt') as f:
    sections = {}
    lines = f.readlines()
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
    i+=2
    number_to_field = defaultdict(set)

    my_ticket = lines[i]
    my_ticket = list(map(int,my_ticket.split(',')))
    for j,num in enumerate(my_ticket):
            valid_for_any = False
            valid_fields = set()
            for field in sections:
                restriction=sections[field]
                if restriction[0][0]<=num<=restriction[0][1] or restriction[1][0]<=num<=restriction[1][1]:
                    valid_fields.add(field)
                    valid_for_any=True
            if valid_for_any and len(valid_fields)>=1:
                number_to_field[j]=valid_fields                

    i+=3
    while i<len(lines):
        numbers = list(map(int,lines[i].split(',')))
        
        for j,num in enumerate(numbers):
            valid_for_any = False
            valid_fields = set()
            for field in sections:
                restriction=sections[field]
                if restriction[0][0]<=num<=restriction[0][1] or restriction[1][0]<=num<=restriction[1][1]:
                    valid_fields.add(field)
                    valid_for_any=True
            if valid_for_any and len(valid_fields)>=1:
                if len(number_to_field)>0:
                    number_to_field[j] = number_to_field[j]&valid_fields

        i+=1
    final = {}
    while True:
        to_remove_val = []
        to_remove_key = []
        for num in number_to_field:
            if len(number_to_field[num])==1:
                final[num]=number_to_field[num]
                to_remove_val.append(list(number_to_field[num])[0])
                to_remove_key.append(num)
        
        if len(to_remove_val)==0:
            break
        for rmv in to_remove_key:
            number_to_field.pop(rmv,None)
        for rmv in to_remove_val:
            for x in number_to_field:
                if rmv in number_to_field[x]:
                    number_to_field[x].remove(rmv)
    res = 1
    cnt = 0
    for num in final:
        val = list(final[num])[0]
        if 'departure' in val:
            res*=my_ticket[num]
            cnt+=1

    print(res,cnt)