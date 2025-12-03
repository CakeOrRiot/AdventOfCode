use std::fs::File;
use std::io::{BufRead, BufReader};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut result: i64 = 0;
    for mb_line in reader.lines() {
        let line = mb_line.unwrap();
        let max1_pos = line
            .char_indices()
            .filter(|&(idx, _)| idx + 1 != line.len())
            .max_by(|&(idx1, c1), &(idx2, c2)| c1.cmp(&c2).then_with(|| idx2.cmp(&idx1)))
            .map(|(idx, _)| idx)
            .unwrap();
        let max2_pos = line
            .char_indices()
            .filter(|&(idx, _)| idx > max1_pos)
            .max_by(|&(idx1, c1), &(idx2, c2)| c1.cmp(&c2).then_with(|| idx2.cmp(&idx1)))
            .map(|(idx, _)| idx)
            .unwrap();
        let num: i64 = format!(
            "{}{}",
            line.chars().nth(max1_pos).unwrap(),
            line.chars().nth(max2_pos).unwrap()
        )
        .parse()
        .unwrap();
        result += num;
    }
    result
}

fn get_nth_max_pos(line: &String, n: usize, last_max_pos: Option<usize>) -> usize {
    let last_pos = match last_max_pos {
        Some(pos) => pos as i64,
        None => -1,
    };
    line.char_indices()
        .filter(|&(idx, _)| idx as i64 > last_pos && idx < line.len() - (12 - n))
        .max_by(|&(idx1, c1), &(idx2, c2)| c1.cmp(&c2).then_with(|| idx2.cmp(&idx1)))
        .map(|(idx, _)| idx)
        .unwrap()
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut result: i64 = 0;
    for mb_line in reader.lines() {
        let line = mb_line.unwrap();
        let mut chars = Vec::new();
        let mut last_max_pos = Option::None;
        for n in 1..=12 {
            let pos = get_nth_max_pos(&line, n, last_max_pos);
            last_max_pos = Some(pos);
            let char = line.chars().nth(pos).unwrap();
            chars.push(char);
        }
        let num: i64 = chars.iter().collect::<String>().parse().unwrap();
        result += num;
    }
    result
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
