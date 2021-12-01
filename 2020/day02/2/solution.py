with open('../input.txt','rt') as f:
    lines = f.readlines()
    valid = 0
    for line in lines:
        tokens =  line.split() 
        a,b = map(lambda x: int(x)-1,tokens[0].split('-'))
        letter = tokens[1][:1]
        password = tokens[2]
        letter_cnt = password.count(letter)
        positions_with_letter = 0
        if password[a]==letter:
            positions_with_letter+=1
        if password[b]==letter:
            positions_with_letter+=1
        if positions_with_letter == 1:
            valid+=1
    print(valid)
