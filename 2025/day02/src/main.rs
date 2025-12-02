use std::fs::File;
use std::io::{BufRead, BufReader};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let mut reader = BufReader::new(file);
    let mut line = String::new();
    let _ = reader.read_line(&mut line);
    let seq: Vec<Vec<&str>> = line.split(",").map(|x| x.split("-").collect()).collect();
    let mut result: i64 = 0;
    for pair in seq {
        let first: i64 = pair[0].trim().parse().unwrap();
        let second: i64 = pair[1].trim().parse().unwrap();
        for i in first..=second {
            let i_str = i.to_string();
            let len = i_str.len();
            if len % 2 != 0 {
                continue;
            }

            let (left, right) = i_str.split_at(len / 2);
            if left == right {
                result += i;
            }
        }
    }
    result
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let mut reader = BufReader::new(file);
    let mut line = String::new();
    let _ = reader.read_line(&mut line);
    let seq: Vec<Vec<&str>> = line.split(",").map(|x| x.split("-").collect()).collect();
    let mut result: i64 = 0;
    for pair in seq {
        let first: i64 = pair[0].trim().parse().unwrap();
        let second: i64 = pair[1].trim().parse().unwrap();
        for i in first..=second {
            let i_str = i.to_string();
            let mut invalid = false;
            for prefix_size in 1..=i_str.len() / 2 {
                if i_str.len() % prefix_size != 0 {
                    continue;
                }
                let mut invalid_prefix = true;
                let (prefix, _) = i_str.split_at(prefix_size);
                for j in (0..i_str.len()).step_by(prefix_size) {
                    let slice = &i_str[j..j + prefix_size];
                    if slice != prefix {
                        invalid_prefix = false;
                        break;
                    }
                }
                if invalid_prefix {
                    invalid = true;
                    break;
                }
            }
            if invalid {
                result += i;
            }
        }
    }
    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
