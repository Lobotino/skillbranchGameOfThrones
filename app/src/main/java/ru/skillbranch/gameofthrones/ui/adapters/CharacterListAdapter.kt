package ru.skillbranch.gameofthrones.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.sbgameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

class CharacterListAdapter : RecyclerView.Adapter<CharacterViewHolder>() {
    private var mCharactersList : List<CharacterItem>? = null

    fun setCharactersList(charactersList : List<CharacterItem>) {
        mCharactersList = charactersList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        mCharactersList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return if(mCharactersList != null) mCharactersList!!.size else 0
    }
}
