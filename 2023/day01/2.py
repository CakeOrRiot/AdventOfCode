import re

res = 0
with open("input.txt") as f:
    for line in f:
        mapping = {
            "one": "1",
            "two": "2",
            "three": "3",
            "four": "4",
            "five": "5",
            "six": "6",
            "seven": "7",
            "eight": "8",
            "nine": "9",
        }

        match1 = re.match(
            "^.*?(\d|one|two|three|four|five|six|seven|eight|nine).*$", line
        )
        match2 = re.match(
            "^.*(\d|one|two|three|four|five|six|seven|eight|nine).*$", line
        )

        res += int(
            mapping.get(match1[1], match1[1]) + mapping.get(match2[1], match2[1])
        )
print(res)
