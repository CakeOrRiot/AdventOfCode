use std::{
    collections::HashSet,
    fs::File,
    io::{BufRead, BufReader},
};

struct Interval {
    left: i64,
    right: i64,
}

fn part1() -> i64 {
    let mut result: i64 = 0;
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut intervals: Vec<Interval> = Vec::new();
    let mut read_ids = false;
    for mb_line in reader.lines() {
        let line = mb_line.unwrap();
        if read_ids {
            let id = line.parse::<i64>().unwrap();
            for interval in &intervals {
                if id >= interval.left && id <= interval.right {
                    result += 1;
                    break;
                }
            }
        } else {
            if line == "" {
                read_ids = true;
                continue;
            }

            let split: Vec<&str> = line.split("-").collect();
            intervals.push(Interval {
                left: split[0].parse::<i64>().unwrap(),
                right: split[1].parse::<i64>().unwrap(),
            });
        }
    }

    result
}

fn part2() -> i64 {
    let mut result: i64 = 0;
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut intervals: Vec<Interval> = Vec::new();
    for mb_line in reader.lines() {
        let line = mb_line.unwrap();
        if line == "" {
            break;
        }

        let split: Vec<&str> = line.split("-").collect();
        intervals.push(Interval {
            left: split[0].parse::<i64>().unwrap(),
            right: split[1].parse::<i64>().unwrap(),
        });
    }
    intervals.sort_by_key(|interval| {
        (
            interval.left,
            interval.right,
            interval.right - interval.left,
        )
    });

    let mut united_intervals: Vec<Interval> = Vec::new();
    let mut i = 0;
    while i < intervals.len() {
        let j = i;
        let mut new_right = intervals[i].right;
        while i + 1 < intervals.len() && intervals[i].right >= intervals[i + 1].left {
            new_right = std::cmp::max(new_right, intervals[i + 1].right);
            i += 1;
        }
        united_intervals.push(Interval {
            left: intervals[j].left,
            right: new_right,
        });
        result += new_right - intervals[j].left + 1;
        i += 1;
    }

    result
}
fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
