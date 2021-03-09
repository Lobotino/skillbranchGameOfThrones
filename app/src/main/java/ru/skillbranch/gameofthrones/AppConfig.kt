package ru.skillbranch.gameofthrones

object AppConfig {
    val HOUSES_LONG_NAME = mapOf(
            Pair("Stark", "House Stark of Winterfell"),
            Pair("Lannister", "House Lannister of Casterly Rock"),
            Pair("Targatyen", "House Targaryen of King's Landing"),
            Pair("Greyjoy", "House Greyjoy of Pyke"),
            Pair("Tyrell", "House Tyrell of Highgarden"),
            Pair("Baratheon", "House Baratheon of Dragonstone"),
            Pair("Martell", "House Nymeros Martell of Sunspear"))

    val HOUSES_SHORT_NAMES = mapOf(
            Pair("House Stark of Winterfell", "Stark"),
            Pair("House Lannister of Casterly Rock", "Lannister"),
            Pair("House Targaryen of King's Landing", "Targatyen"),
            Pair("House Greyjoy of Pyke", "Greyjoy"),
            Pair("House Tyrell of Highgarden", "Tyrell"),
            Pair("House Baratheon of Dragonstone", "Baratheon"),
            Pair("House Nymeros Martell of Sunspear", "Martell"))

    const val BASE_URL = "https://www.anapioficeandfire.com/api/"
}