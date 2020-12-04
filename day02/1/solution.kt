import java.io.File
//454 649
fun main(){
    var lines = File("../input.txt").readLines()
    var validPasswords = 0
    for(line in lines){
        var tokens = line.split(" ")
        var l = tokens[0].split("-").map{x -> x.toInt()}
        var a = l[0]
        var b = l[1]
        var letter = tokens[1].slice(0..0)[0]
        var password = tokens[2]
        var letterCnt = password.count{x->x==letter}
        if(a<=letterCnt && letterCnt<=b)
        {
            validPasswords++
        }
    }
    println(validPasswords)
}