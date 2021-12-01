import java.io.File

fun main(){
    var lines = File("input.txt").readLines().map{line->line.toInt()}.toSet()
    for (x in lines) {
        if(lines.contains(2020-x))
        {
            println(x*(2020-x))
        }
    }
}