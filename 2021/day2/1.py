import sys

depth = 0
x = 0
with open(sys.argv[1],'rt') as f:
	for line in f:
		direction, val = line.split()
		val = int(val)
		if direction == 'forward':
			x+=val
		if direction == 'down':
			depth+=val
		if direction == 'up':
			depth-=val
print(depth*x)			