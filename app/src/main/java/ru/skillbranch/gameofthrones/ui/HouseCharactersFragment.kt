package ru.skillbranch.gameofthrones.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.sbgameofthrones.R
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesClient
import ru.skillbranch.gameofthrones.interactors.MainInteractor
import ru.skillbranch.gameofthrones.presenters.HouseCharactersContract
import ru.skillbranch.gameofthrones.presenters.HouseCharactersPresenter
import ru.skillbranch.gameofthrones.repositories.local.LocalCharactersRepository
import ru.skillbranch.gameofthrones.repositories.local.LocalHousesRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineCharactersRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineHousesRepository
import ru.skillbranch.gameofthrones.ui.adapters.CharacterListAdapter

class HouseCharactersFragment(private val houseShortName : String) : Fragment(), HouseCharactersContract.View {

    private val mApi = GameOfThronesClient().getApiClient()
    private val mPresenter = HouseCharactersPresenter(
        MainInteractor(
            OnlineHousesRepository(mApi),
            OnlineCharactersRepository(mApi),
            LocalHousesRepository(App.app!!.getContext()),
            LocalCharactersRepository(App.app!!.getContext())
        )
    )

    private val mCharactersAdapter = CharacterListAdapter()

    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragment = inflater.inflate(R.layout.character_list_fragment, container, false)
        mRecyclerView = fragment.findViewById<RecyclerView>(R.id.recyclerCharacterList)
        mRecyclerView?.adapter = mCharactersAdapter
        mRecyclerView?.layoutManager = LinearLayoutManager(activity)

        mPresenter.bind(this)
        mPresenter.onHouseClick(houseShortName)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unbind()
    }

    override fun showHouseCharacters(characters: List<CharacterItem>) {
        mCharactersAdapter.setCharactersList(characters)
    }

    override fun showFullCharacter(characterId: String) {
        TODO("Not yet implemented")
    }
}