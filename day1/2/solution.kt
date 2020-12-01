import java.io.File

fun main(){
    var lines = File("input.txt").readLines().map{line->line.toInt()}.toSet()
    for (x in lines) {
        for(y in lines){
            if(lines.contains(2020-x-y))
            {
                println(x*y*(2020-x-y))
            }
        }
    }
}