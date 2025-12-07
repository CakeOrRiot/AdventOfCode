use std::{
    collections::{HashSet, VecDeque},
    fs, result,
};
#[derive(Debug, PartialEq, Eq, Hash)]
struct Beam {
    x: usize,
    y: usize,
}

fn part1() -> i64 {
    let input: Vec<Vec<char>> = fs::read_to_string(&"input")
        .unwrap()
        .lines()
        .map(|x| x.chars().collect())
        .collect();

    let mut beams: VecDeque<Beam> = VecDeque::new();
    let mut result: i64 = 0;
    for i in 0..input[0].len() {
        if input[0][i] == 'S' {
            beams.push_back(Beam { x: 0, y: i });
        }
    }
    while !beams.is_empty() {
        let mut new_beams: HashSet<Beam> = HashSet::new();
        while !beams.is_empty() {
            let beam_pos = beams.pop_front().unwrap();
            if beam_pos.x + 1 == input.len() {
                continue;
            }
            let next_pos = Beam {
                x: beam_pos.x + 1,
                y: beam_pos.y,
            };

            if input[next_pos.x][next_pos.y] == '^' {
                if beam_pos.y + 1 < input[0].len() {
                    new_beams.insert(Beam {
                        x: beam_pos.x + 1,
                        y: beam_pos.y + 1,
                    });
                }
                if beam_pos.y - 1 < input[0].len() {
                    new_beams.insert(Beam {
                        x: beam_pos.x + 1,
                        y: beam_pos.y - 1,
                    });
                }
                result += 1;
            } else {
                new_beams.insert(next_pos);
            }
        }
        for beam in new_beams {
            beams.push_back(beam);
        }
    }
    result
}

fn part2() -> i64 {
    let input: Vec<Vec<char>> = fs::read_to_string(&"input")
        .unwrap()
        .lines()
        .map(|x| x.chars().collect())
        .collect();
    let mut dp: Vec<Vec<i64>> = vec![vec![0; input[0].len()]; input.len()];
    for i in 0..input[0].len() {
        if input[0][i] == 'S' {
            dp[0][i] = 1;
        }
    }
    for i in 1..input.len() {
        for j in 0..input[0].len() {
            if input[i - 1][j] == '^' {
                dp[i][j + 1] += dp[i - 1][j];
                dp[i][j - 1] += dp[i - 1][j];
            } else {
                dp[i][j] += dp[i - 1][j];
            }
        }
    }
    let mut result: i64 = 0;
    for i in 0..input[0].len() {
        result += dp[input.len() - 1][i];
    }
    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
