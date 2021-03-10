package ru.skillbranch.gameofthrones.presenters

import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

interface HouseCharactersContract {

    interface Presenter {
        fun bind(view : View)

        fun unbind()

        fun onHouseClick(houseName : String)

        fun onCharacterClick(characterId : String)
    }

    interface View {
        fun showHouseCharacters(characters : List<CharacterItem>)

        fun showFullCharacter(characterId : String)
    }
}