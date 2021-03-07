package ru.skillbranch.gameofthrones

object AppConfig {
    val HOUSES = mapOf(
            Pair("Stark", "House Stark of Winterfell"),
            Pair("Lannister", "House Lannister of Casterly Rock"),
            Pair("Targatyen", "House Targaryen of King's Landing"),
            Pair("Greyjoy", "House Greyjoy of Pyke"),
            Pair("Tyrell", "House Tyrell of Highgarden"),
            Pair("Baratheon", "House Baratheon of Dragonstone"),
            Pair("Martell", "House Nymeros Martell of Sunspear"))

    const val BASE_URL = "https://www.anapioficeandfire.com/api/"
}