package ru.skillbranch.gameofthrones.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.repositories.CharactersRepository
import ru.skillbranch.gameofthrones.repositories.HousesRepository
import java.util.regex.Pattern

class HousesInteractor(private val housesRepository: HousesRepository, private val charactersRepository: CharactersRepository) {

    companion object {
        const val CHARACTER_REGEX = "https://www.anapioficeandfire.com/api/characters/([0-9]+)"
    }

    private var job : Job? = null

    fun getOnlineHousesCharacters(vararg houseNames: String, result: (houses: List<Pair<HouseRes, List<CharacterRes>>>) -> Unit) {
        val resultList = ArrayList<Pair<HouseRes, List<CharacterRes>>>()

        job?.cancel()

        job = GlobalScope.launch(Dispatchers.IO) {
            for (house in houseNames) {
                getHouseResByName(house)?.let {
                    resultList.add(Pair(it, getCharactersResListByHouseMembers(it.swornMembers)))
                }
            }
            result(resultList)
        }
    }

    private fun getHouseResByName(houseName : String) : HouseRes? {
        housesRepository.getCurrentHouse(houseName)?.let {
            return it
        }
        return null
    }

    private fun getCharactersResListByHouseMembers(swornMembers : List<String>) : List<CharacterRes> {
        val charactersList = ArrayList<CharacterRes>()

        for (char in swornMembers) {
            val charId = parseCharacterUrl(char)
            if (charId == 0L) continue

            val characterRes = charactersRepository.getCharacterById(charId)
            if(characterRes != null) {
                charactersList.add(characterRes)
            }
        }

        return charactersList
    }

    private fun parseCharacterUrl(url: String): Long {
        try {
            if (Pattern.matches(CHARACTER_REGEX, url)) {
                val pattern = Pattern.compile(CHARACTER_REGEX)
                val matcher = pattern.matcher(url)
                if (matcher.find()) {
                    return matcher.group(1).toLong()
                }
            }
        } catch (exception : Exception) {
            exception.printStackTrace()
        }
        return 0L
    }
}