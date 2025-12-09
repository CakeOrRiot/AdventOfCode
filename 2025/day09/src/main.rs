use std::{
    fs::File,
    io::{BufRead, BufReader},
};

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut numbers: Vec<(i64, i64)> = Vec::new();
    for line in reader.lines() {
        let line = line.unwrap();
        let line_numbers: Vec<i64> = line.split(',').map(|s| s.parse().unwrap()).collect();
        numbers.push((line_numbers[0], line_numbers[1]));
    }
    let mut max_area = 0;
    for i in 0..numbers.len() {
        for j in i + 1..numbers.len() {
            let area = ((numbers[i].0 - numbers[j].0).abs() + 1)
                * ((numbers[i].1 - numbers[j].1).abs() + 1);
            if area > max_area {
                max_area = area;
            }
        }
    }
    max_area
}

fn point_inside(x: i64, y: i64, edges: &Vec<(i64, i64)>) -> bool {
    let mut crossings = 0;
    for i in 0..edges.len() {
        let (x1, y1) = edges[i];
        let (x2, y2) = edges[(i + 1) % edges.len()];
        if x1 == x2 {
            let min_y = y1.min(y2);
            let max_y = y1.max(y2);
            if min_y <= y && y < max_y && x1 > x {
                crossings += 1;
            }
        }
    }

    return crossings % 2 == 1;
}

fn rectangle_inside(
    mut rx1: i64,
    mut ry1: i64,
    mut rx2: i64,
    mut ry2: i64,
    edges: &Vec<(i64, i64)>,
) -> bool {
    let minx = rx1.min(rx2);
    let maxx = rx1.max(rx2);
    rx1 = minx;
    rx2 = maxx;

    let miny = ry1.min(ry2);
    let maxy = ry1.max(ry2);
    ry1 = miny;
    ry2 = maxy;

    for i in 0..edges.len() {
        let (x1, y1) = edges[i];
        let (x2, y2) = edges[(i + 1) % edges.len()];

        if x1 == x2 {
            let ey_min = y1.min(y2);
            let ey_max = y1.max(y2);
            if rx1 < x1 && x1 < rx2 && ey_min < ry2 && ey_max > ry1 {
                return false;
            }
        } else {
            let ex_min = x1.min(x2);
            let ex_max = x1.max(x2);
            if ry1 < y1 && y1 < ry2 && ex_min < rx2 && ex_max > rx1 {
                return false;
            }
        }
    }

    let cx = (rx1 + rx2) / 2;
    let cy = (ry1 + ry2) / 2;
    if point_inside(cx, cy, edges) {
        return true;
    }
    false
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut edges: Vec<(i64, i64)> = Vec::new();
    for line in reader.lines() {
        let line = line.unwrap();
        let coords: Vec<i64> = line.split(',').map(|s| s.parse().unwrap()).collect();
        edges.push((coords[0], coords[1]));
    }

    let mut max_area = 0;
    for i in 0..edges.len() {
        for j in (i + 1)..edges.len() {
            let area =
                ((edges[i].0 - edges[j].0).abs() + 1) * ((edges[i].1 - edges[j].1).abs() + 1);
            if area > max_area {
                let rx1 = edges[i].0;
                let ry1 = edges[i].1;
                let rx2 = edges[j].0;
                let ry2 = edges[j].1;
                if rectangle_inside(rx1, ry1, rx2, ry2, &edges) {
                    max_area = area;
                }
            }
        }
    }
    max_area
}
fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
