use std::collections::HashMap;
use std::fs;

const FILE_PATH: &str = "input.txt";

fn solve_1() -> i32 {
    let contents = fs::read_to_string(FILE_PATH).expect("Unable to read file");
    let lines: Vec<Vec<&str>> = contents
        .split('\n')
        .map(|s| s.split(' ').collect())
        .filter(|s: &Vec<&str>| s.len() == 2)
        .collect();

    let mut decoder: HashMap<&str, &str> = HashMap::new();
    decoder.insert("X", "A");
    decoder.insert("Y", "B");
    decoder.insert("Z", "C");

    let mut scores: HashMap<&str, i32> = HashMap::new();
    scores.insert("A", 1);
    scores.insert("B", 2);
    scores.insert("C", 3);

    let mut beats: HashMap<&str, &str> = HashMap::new();
    beats.insert("A", "C");
    beats.insert("B", "A");
    beats.insert("C", "B");

    let mut score = 0;
    for line in &lines {
        let opponent = line[0];
        let me = decoder[line[1]];
        score += scores[me];
        if beats[me] == opponent {
            score += 6;
        }
        if opponent == me {
            score += 3;
        }
    }
    return score;
}

fn solve_2() -> i32 {
    let contents = fs::read_to_string(FILE_PATH).expect("Unable to read file");
    let lines: Vec<Vec<&str>> = contents
        .split('\n')
        .map(|s| s.split(' ').collect())
        .filter(|s: &Vec<&str>| s.len() == 2)
        .collect();

    let mut scores: HashMap<&str, i32> = HashMap::new();
    scores.insert("A", 1);
    scores.insert("B", 2);
    scores.insert("C", 3);

    let mut beats: HashMap<&str, &str> = HashMap::new();
    beats.insert("A", "C");
    beats.insert("B", "A");
    beats.insert("C", "B");

    let mut looses: HashMap<&str, &str> = HashMap::new();
    looses.insert("C", "A");
    looses.insert("A", "B");
    looses.insert("B", "C");

    let mut score = 0;
    for line in &lines {
        let opponent = line[0];
        let result = line[1];
        let me = match result {
            "X" => Ok(beats[opponent]),
            "Y" => Ok(opponent),
            "Z" => Ok(looses[opponent]),
            _ => Err("???"),
        }
        .unwrap();

        score += scores[me];
        if beats[me] == opponent {
            score += 6;
        }
        if opponent == me {
            score += 3;
        }
    }
    return score;
}

fn main() {
    print!("{}\n", solve_1());
    print!("{}\n", solve_2());
}
