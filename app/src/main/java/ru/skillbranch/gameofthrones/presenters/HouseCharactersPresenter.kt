package ru.skillbranch.gameofthrones.presenters

import ru.skillbranch.gameofthrones.interactors.MainInteractor

class HouseCharactersPresenter(private val mainInteractor: MainInteractor) : HouseCharactersContract.Presenter {

    private var mView : HouseCharactersContract.View? = null

    override fun bind(view: HouseCharactersContract.View) {
        mView = view
    }

    override fun unbind() {
        mView = null
    }

    override fun onHouseClick(houseName: String) {
        mainInteractor.findCharactersByHouseName(houseName) {
            mView?.showHouseCharacters(it)
        }
    }

    override fun onCharacterClick(characterId: String) {
        mView?.showFullCharacter(characterId)
    }
}