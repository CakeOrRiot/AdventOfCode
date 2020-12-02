import java.io.File
fun main(){
    var lines = File("../input.txt").readLines()
    var validPasswords = 0
    for(line in lines){
        var tokens = line.split(" ")
        var l = tokens[0].split("-").map{x -> x.toInt()-1}
        var a = l[0]
        var b = l[1]
        var letter = tokens[1].slice(0..0)[0]
        var password = tokens[2]
        var positionsWithLetter = 0
        if(password[a]==letter){
            positionsWithLetter++
        }
        if(password[b]==letter){
            positionsWithLetter++
        }
        if(positionsWithLetter==1){
            validPasswords++
        }
    }
    println(validPasswords)
}