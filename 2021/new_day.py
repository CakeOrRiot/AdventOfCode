from dotenv import load_dotenv
import requests
import sys
import os

load_dotenv()
day = sys.argv[1]
os.mkdir(f'day{day}')
cookie = {'session': os.getenv('cookie')}
resp = requests.get(f'https://adventofcode.com/2021/day/{day}/input',cookies=cookie)
with open(f'day{day}/input.txt','wt') as f:
	f.write(resp.text)