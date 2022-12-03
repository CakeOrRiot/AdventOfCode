use std::collections::HashSet;
use std::fs;
use std::fs::File;
use std::io::{prelude::*, BufReader};

const FILE_PATH: &str = "input.txt";

fn char_to_int(ch: char) -> u32 {
    if ch.is_ascii_lowercase() {
        return (ch as u32) - ('a' as u32) + 1;
    }

    return (ch as u32) - ('A' as u32) + 27;
}

fn solve_1() -> u32 {
    let contents = fs::read_to_string(FILE_PATH).expect("Unable to read file");
    let lines: Vec<Vec<u32>> = contents
        .split('\n')
        .map(|s| s.chars().map(|ch| char_to_int(ch)).collect())
        .collect();

    let mut sum = 0;
    for line in &lines {
        let n = line.len();
        let mut left = HashSet::new();
        for x in &line[..n / 2] {
            left.insert(*x);
        }

        let mut right = HashSet::new();
        for x in &line[n / 2..] {
            right.insert(*x);
        }
        let intersection: HashSet<u32> = left.intersection(&right).map(|x| *x).collect();
        sum += intersection.iter().sum::<u32>();
    }
    return sum;
}

fn process_group(group: &Vec<String>) -> u32 {
    let mut intersection: HashSet<char> = HashSet::new();
    let mut is_first = true;
    let mut sum = 0;
    for l in group {
        let mut chars: HashSet<char> = HashSet::new();
        for ch in l.chars() {
            chars.insert(ch);
        }
        if is_first {
            intersection = chars;
            is_first = false;
        } else {
            intersection = chars.intersection(&intersection).map(|x| *x).collect();
        }
    }
    sum += intersection.iter().map(|ch| char_to_int(*ch)).sum::<u32>();
    return sum;
}

fn solve_2() -> u32 {
    let file = File::open(FILE_PATH).expect("Can't read file");
    let lines = BufReader::new(file).lines();
    let mut group: Vec<String> = Vec::new();
    let mut sum = 0;
    for line in lines {
        if group.len() == 3 {
            sum += process_group(&group);
            group.clear();
        }
        group.push(line.unwrap());
    }
    sum += process_group(&group);
    return sum;
}

fn main() {
    println!("{}", solve_1());
    println!("{}", solve_2());
}
