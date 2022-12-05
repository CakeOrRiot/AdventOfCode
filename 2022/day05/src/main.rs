use itertools::Itertools;
use std::fs::File;
use std::io::{prelude::*, BufReader};

const FILE_PATH: &str = "input.txt";

struct Move {
    count: usize,
    from: usize,
    to: usize,
}

fn solve_1() -> String {
    let file = File::open(FILE_PATH).expect("Can't read file");
    let all_lines: Vec<String> = BufReader::new(file).lines().map(|s| s.unwrap()).collect();

    let mut state: Vec<Vec<char>> = [].to_vec();
    for _ in 0..9 {
        state.push([].to_vec());
    }

    for line in all_lines.iter().take(8) {
        let iter: Vec<Vec<char>> = line
            .chars()
            .chunks(4)
            .into_iter()
            .map(|x| x.collect())
            .collect();
        let mut i = 0;
        for dock in iter {
            if dock[1] != ' ' {
                state[i].push(dock[1]);
            }
            i += 1;
        }
    }

    for i in 0..state.len() {
        state[i].reverse();
    }

    let moves: Vec<Move> = all_lines
        .iter()
        .skip(10)
        .map(|s| {
            let sp: Vec<&str> = s.split(' ').collect();
            return Move {
                count: sp[1].parse::<usize>().unwrap(),
                from: sp[3].parse::<usize>().unwrap() - 1,
                to: sp[5].parse::<usize>().unwrap() - 1,
            };
        })
        .collect();

    for mv in moves {
        for _ in 0..mv.count {
            let item = state[mv.from].pop().unwrap();
            state[mv.to as usize].push(item);
        }
    }

    return state.into_iter().map(|s| s[s.len() - 1]).collect();
}

fn solve_2() -> String {
    let file = File::open(FILE_PATH).expect("Can't read file");
    let all_lines: Vec<String> = BufReader::new(file).lines().map(|s| s.unwrap()).collect();

    let mut state: Vec<Vec<char>> = [].to_vec();
    for _ in 0..9 {
        state.push([].to_vec());
    }

    for line in all_lines.iter().take(8) {
        let iter: Vec<Vec<char>> = line
            .chars()
            .chunks(4)
            .into_iter()
            .map(|x| x.collect())
            .collect();
        let mut i = 0;
        for dock in iter {
            if dock[1] != ' ' {
                state[i].push(dock[1]);
            }
            i += 1;
        }
    }

    for i in 0..state.len() {
        state[i].reverse();
    }

    let moves: Vec<Move> = all_lines
        .iter()
        .skip(10)
        .map(|s| {
            let sp: Vec<&str> = s.split(' ').collect();
            return Move {
                count: sp[1].parse::<usize>().unwrap(),
                from: sp[3].parse::<usize>().unwrap() - 1,
                to: sp[5].parse::<usize>().unwrap() - 1,
            };
        })
        .collect();

    for mv in moves {
        let mut to = std::mem::take(&mut state[mv.to]);
        let moveable = {
            let iter = &mut state[mv.from];
            iter.drain(iter.len() - mv.count..)
        };
        to.extend(moveable);
        state[mv.to] = to;
    }

    return state.into_iter().map(|s| s[s.len() - 1]).collect();
}

fn main() {
    println!("{}", solve_1());
    println!("{}", solve_2());
}
