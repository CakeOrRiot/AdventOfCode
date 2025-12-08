use std::{
    cmp::Reverse,
    fs::File,
    io::{BufRead, BufReader},
};

#[derive(Clone, Copy)]
struct Point {
    x: i64,
    y: i64,
    z: i64,
}

struct DSU {
    parent: Vec<usize>,
    size: Vec<usize>,
}
impl DSU {
    fn new(n: usize) -> Self {
        DSU {
            parent: (0..n).collect(),
            size: vec![1; n],
        }
    }

    fn find(&mut self, a: usize) -> usize {
        if self.parent[a] != a {
            self.parent[a] = self.find(self.parent[a]);
        }
        self.parent[a]
    }

    fn union(&mut self, a: usize, b: usize) {
        let root_a = self.find(a);
        let root_b = self.find(b);
        if root_a != root_b {
            if self.size[root_a] < self.size[root_b] {
                self.parent[root_a] = root_b;
                self.size[root_b] += self.size[root_a];
            } else {
                self.parent[root_b] = root_a;
                self.size[root_a] += self.size[root_b];
            }
        }
    }
}

fn distance(a: &Point, b: &Point) -> i64 {
    (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z)
}

fn part1() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut points: Vec<Point> = Vec::new();
    for line in reader.lines() {
        let line = line.unwrap();
        let coords: Vec<i64> = line.split(',').map(|s| s.parse::<i64>().unwrap()).collect();
        points.push(Point {
            x: coords[0],
            y: coords[1],
            z: coords[2],
        });
    }
    let mut distances: Vec<(i64, usize, usize)> = Vec::new();

    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            let distance = distance(&points[i], &points[j]);
            distances.push((distance, i, j));
        }
    }
    distances.sort();

    let mut set = DSU::new(points.len());
    let iterations = 1000;
    for (_, i, j) in distances.iter().take(iterations) {
        set.union(*i, *j);
    }
    let mut roots = Vec::new();

    for i in 0..points.len() {
        let root = set.find(i);
        roots.push((root, set.size[root]));
    }

    roots.sort_by_key(|k| Reverse(k.1));
    roots.dedup_by_key(|k| k.0);
    let mut result: i64 = 1;
    for root in roots.iter().take(3) {
        result *= root.1 as i64;
    }

    result
}

fn part2() -> i64 {
    let file = File::open("input").unwrap();
    let reader = BufReader::new(file);
    let mut points: Vec<Point> = Vec::new();
    for line in reader.lines() {
        let line = line.unwrap();
        let coords: Vec<i64> = line.split(',').map(|s| s.parse::<i64>().unwrap()).collect();
        points.push(Point {
            x: coords[0],
            y: coords[1],
            z: coords[2],
        });
    }
    let mut distances: Vec<(i64, usize, usize)> = Vec::new();

    for i in 0..points.len() {
        for j in (i + 1)..points.len() {
            let distance = distance(&points[i], &points[j]);
            distances.push((distance, i, j));
        }
    }
    distances.sort();

    let mut set = DSU::new(points.len());
    let mut num_of_sets = points.len();
    for (_, i, j) in distances {
        if set.find(i) != set.find(j) {
            num_of_sets -= 1;
        }
        if num_of_sets == 1 {
            return points[i].x * points[j].x;
        }
        set.union(i, j);
    }
    panic!("Something went wrong");
}

fn main() {
    println!("{}", part1());
    println!("{}", part2());
}
