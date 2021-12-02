import sys

depth = 0
x = 0
aim = 0
with open(sys.argv[1],'rt') as f:
	for line in f:
		direction, val = line.split()
		val = int(val)
		if direction == 'forward':
			x+=val
			depth += aim*val 
		if direction == 'down':
			aim += val
		if direction == 'up':
			aim -= val
print(depth*x)			