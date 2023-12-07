import java.io.File

class Day07 {

    fun solve1() {

        var hands = File("inputs/7.txt").inputStream().bufferedReader().lineSequence().map { x ->
            val split = x.split(' ')
            val highCardsMapping = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11, 'T' to 10)
            val cards = split[0].map {
                if (highCardsMapping.containsKey(it)) highCardsMapping.getOrDefault(it, 1) else it.toString().toInt()
            }
            Hand1(cards, split[1].toInt())
        }.toList()
        hands = hands.sorted()
        var res = 0L
        for (i in 1..hands.count()) {
            res += i * hands[i - 1].bid
        }
        println(res)
    }

    fun solve2() {
        var hands = File("inputs/7.txt").inputStream().bufferedReader().lineSequence().map { x ->
            val split = x.split(' ')
            val highCardsMapping = mapOf(
                'A' to 14,
                'K' to 13,
                'Q' to 12,
                'T' to 11,
                '9' to 10,
                '8' to 9,
                '7' to 8,
                '6' to 7,
                '5' to 6,
                '4' to 5,
                '3' to 4,
                '2' to 3,
                'J' to 2,
            )
            val cards = split[0].map {
                highCardsMapping.getOrDefault(it, 1)
            }
            Hand2(cards, split[1].toInt())
        }.toList()
        hands = hands.sorted()
        var res = 0L
        for (i in 1..hands.count()) {
            res += i * hands[i - 1].bid
        }
        println(res)
    }
}

data class Hand1(val cards: List<Int>, val bid: Int) : Comparable<Hand1> {

    fun getType(): Int {
        val counts = cards.groupingBy { it }.eachCount()
        val maxCount = counts.values.max()
        return when (maxCount) {
            5 -> 7
            4 -> 6
            3 -> if (counts.values.min() == 2) 5 else 4
            2 -> if (counts.filterValues { x -> x == 2 }.count() == 2) 3 else 2
            1 -> 1
            else -> throw Exception()
        }
    }

    override fun compareTo(other: Hand1): Int {
        val type = getType()
        val otherType = other.getType()
        if (type > otherType) return 1
        else if (type < otherType) return -1
        else {
            for ((card, otherCard) in cards.zip(other.cards)) {
                if (card > otherCard) return 1
                else if (card < otherCard) return -1

            }
            return 0
        }

    }
}

data class Hand2(val cards: List<Int>, val bid: Int) : Comparable<Hand2> {
    private fun getType(): Int {
        return (2..14).maxOf { replacement ->
            val changedCards = cards.map { x -> if (x == 2) replacement else x }
            getTypeNoJacks(changedCards)
        }
    }

    private fun getTypeNoJacks(cards: List<Int>): Int {
        val counts = cards.groupingBy { it }.eachCount()
        val maxCount = counts.values.max()
        return when (maxCount) {
            5 -> 7
            4 -> 6
            3 -> if (counts.values.min() == 2) 5 else 4
            2 -> if (counts.filterValues { x -> x == 2 }.count() == 2) 3 else 2
            1 -> 1
            else -> throw Exception()
        }
    }

    override fun compareTo(other: Hand2): Int {
        val type = getType()
        val otherType = other.getType()
        if (type > otherType) return 1
        else if (type < otherType) return -1
        else {
            for ((card, otherCard) in cards.zip(other.cards)) {
                if (card > otherCard) return 1
                else if (card < otherCard) return -1

            }
            return 0
        }

    }
}
