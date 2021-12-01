with open('../input.txt','rt') as f:
    lines = f.readlines()
    valid = 0
    for line in lines:
        tokens =  line.split() 
        a,b = map(int,tokens[0].split('-'))
        letter = tokens[1][:1]
        password = tokens[2]
        letter_cnt = password.count(letter)
        if a<=letter_cnt<=b:
            valid+=1
    print(valid)
