import sys


with open(sys.argv[1],'rt') as f:
	values = []
	for line in f:
		x = int(line)
		values.append(x)
		
res = 0

for i,x in enumerate(values):
	if i>2 and x > values[i-3]:
		res+=1

print(res)