use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn part1() -> i32 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut cur_pos = 50;
    let mut result = 0;
    for line in reader.lines() {
        let line = line.unwrap();
        let (direction, s_shift) = line.split_at(1);
        let shift: i32 = s_shift.parse().unwrap();
        if direction == "L" {
            cur_pos -= shift;
        } else {
            cur_pos += shift;
        }
        cur_pos = (cur_pos + 100) % 100;
        if cur_pos == 0 {
            result += 1;
        }
    }
    result
}

fn part2() -> i32 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut cur_pos = 50;
    let mut result = 0;
    for line in reader.lines() {
        let line = line.unwrap();
        let (direction, s_shift) = line.split_at(1);
        let mut shift: i32 = s_shift.parse().unwrap();
        let start_pos = cur_pos;
        result += shift / 100;
        shift = shift % 100;
        if direction == "L" {
            cur_pos -= shift;
        } else {
            cur_pos += shift;
        }
        if (cur_pos <= 0 || cur_pos >= 100) && start_pos != 0 {
            result += 1;
        }
        cur_pos = (cur_pos + 100) % 100;
        // println!("{}, {}, {}", line, cur_pos, result);
    }
    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
