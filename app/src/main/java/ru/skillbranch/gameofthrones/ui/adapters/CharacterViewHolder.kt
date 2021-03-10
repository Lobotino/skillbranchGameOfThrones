package ru.skillbranch.gameofthrones.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.sbgameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

class CharacterViewHolder : RecyclerView.ViewHolder {

    private var name : TextView? = null;
    private var titlesAndAliases : TextView? = null;
    private var houseImg : ImageView? = null;

    constructor(itemView : View) : super(itemView) {
        name = itemView.findViewById(R.id.character_name)
        titlesAndAliases = itemView.findViewById(R.id.character_titles)
        houseImg = itemView.findViewById(R.id.house_img)
    }

    fun bind(info : CharacterItem) {
        name?.text = info.name
        titlesAndAliases?.text = prepareTitlesAndAliases(info.titles, info.aliases)
//        houseImg = info.house
    }

    private fun prepareTitlesAndAliases(titles : List<String>, aliases : List<String>) : String {
        var result = ""
        for (title in titles) {
            result += "•$title"
        }
        for (alias in aliases) {
            result += "•$alias"
        }
        return if (result.isNotEmpty()) result else "Information is unknown"
    }
}