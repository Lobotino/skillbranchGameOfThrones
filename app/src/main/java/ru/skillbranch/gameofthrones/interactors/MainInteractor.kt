package ru.skillbranch.gameofthrones.interactors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.repositories.local.LocalCharactersRepository
import ru.skillbranch.gameofthrones.repositories.local.LocalHousesRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineCharactersRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineHousesRepository
import java.io.IOException
import java.util.regex.Pattern

class MainInteractor(private val onlineHousesRepository: OnlineHousesRepository,
                     private val onlineCharactersRepository: OnlineCharactersRepository,
                     private val localHousesRepository: LocalHousesRepository,
                     private val localCharactersRepository: LocalCharactersRepository) {

    companion object {
        const val CHARACTER_REGEX = "https://www.anapioficeandfire.com/api/characters/([0-9]+)"
    }

    private var jobGetOnlineHousesCharacters: Job? = null
    private var jobGetAllHouses: Job? = null
    private var jobGetNeedHouses: Job? = null

    fun getAllHousesOnline(result: (houses: List<HouseRes>) -> Unit, onError: (error : IOException) -> Unit) {
        jobGetAllHouses?.cancel()
        jobGetAllHouses = GlobalScope.launch(Dispatchers.IO) {
            try {
                val resultList = ArrayList<HouseRes>()
                var i: Long = 1
                do {
                    val pageList = onlineHousesRepository.getHouses(i++, 50)
                    if (pageList.isNotEmpty()) {
                        resultList.addAll(pageList)
                    }
                } while (pageList.isNotEmpty())
                result(resultList)
            } catch (ex : IOException) {
                onError(ex)
            }
        }
    }

    fun getNeedHousesOnline(vararg houseNames: String, result: (houses: List<HouseRes>) -> Unit) {
        jobGetNeedHouses?.cancel()
        jobGetNeedHouses = GlobalScope.launch(Dispatchers.IO) {
            val resultList = ArrayList<HouseRes>()
            for (house in houseNames) {
                onlineHousesRepository.getCurrentHouse(house)?.let {
                    resultList.add(it)
                }
            }
            result(resultList)
        }
    }

    fun getHousesAndCharactersOnline(vararg houseNames: String, result: (houses: List<Pair<HouseRes, List<CharacterRes>>>) -> Unit, onError: (error : IOException) -> Unit) {
        jobGetOnlineHousesCharacters?.cancel()
        jobGetOnlineHousesCharacters = GlobalScope.launch(Dispatchers.IO) {
            try {
                val resultList = ArrayList<Pair<HouseRes, List<CharacterRes>>>()
                for (house in houseNames) {
                    getHouseResByName(house)?.let {
                        resultList.add(Pair(it, getCharactersResListByHouseMembers(it.swornMembers)))
                    }
                }
                result(resultList)
            } catch (ex : IOException) {
                onError(ex)
            }
        }
    }

    private fun getHouseResByName(houseName: String): HouseRes? {
        onlineHousesRepository.getCurrentHouse(houseName)?.let {
            return it
        }
        return null
    }

    private fun getCharactersResListByHouseMembers(swornMembers: List<String>): List<CharacterRes> {
        val charactersList = ArrayList<CharacterRes>()

        for (char in swornMembers) {
            val charId = parseCharacterUrl(char)
            if (charId == 0L) continue

            val characterRes = onlineCharactersRepository.getCharacterById(charId)
            if (characterRes != null) {
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
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return 0L
    }

    fun saveHousesLocal(vararg houses: HouseRes, onComplete: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            localHousesRepository.saveHouses(*houses)
            onComplete()
        }
    }

    fun saveCharactersLocal(vararg characters: CharacterRes, onComplete: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            localCharactersRepository.saveCharacters(charactersRes = *characters, parseParentIdFromUrl = ::parseCharacterUrl)
            onComplete()
        }
    }

    fun findCharacterFullById(id : String, result: (character : CharacterFull) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            result(localCharactersRepository.getFullCharacter(id, parseParentIdFromUrl = ::parseCharacterUrl))
        }
    }

    fun findCharactersByHouseName(houseName : String, result: (characters : List<CharacterItem>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            result(localCharactersRepository.getCharactersItems(houseName))
        }
    }
}