package ru.skillbranch.gameofthrones.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import java.lang.Exception

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME : String = "GameOfThrones"
        const val DATABASE_VERSION = 1

        private const val TABLE_HOUSES = "houses"
        private const val TABLE_HOUSES_TITLES = "houses_titles"
        private const val TABLE_HOUSES_SEATS = "houses_seats"
        private const val TABLE_HOUSES_ANCESTRAL_WEAPONS = "houses_ancestral_weapons"
        private const val TABLE_HOUSES_CADET_BRANCHES = "houses_cadet_branches"
        private const val TABLE_HOUSES_SWORN_MEMBERS = "houses_sworn_members"

        private const val TABLE_CHARACTERS = "characters"
        private const val TABLE_CHARACTERS_TITLES = "characters_titles"
        private const val TABLE_CHARACTERS_ALIASES = "characters_aliases"
        private const val TABLE_CHARACTERS_ALLEGIANCES = "characters_allegiances"
        private const val TABLE_CHARACTERS_BOOKS = "characters_books"
        private const val TABLE_CHARACTERS_POV_BOOKS = "characters_pov_books"
        private const val TABLE_CHARACTERS_TV_SERIES = "characters_tv_series"
        private const val TABLE_CHARACTERS_PLAYED_BY = "characters_played_by"

        //houses region
        private const val COLUMN_URL = "url"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_FULL_NAME = "full_name"
        private const val COLUMN_REGION = "region"
        private const val COLUMN_COAT_OF_ARMS = "coat_of_arms"
        private const val COLUMN_WORDS = "words"
        private const val COLUMN_CURRENT_LORD = "current_lord"
        private const val COLUMN_HEIR = "heir"
        private const val COLUMN_OVERLORD = "overlord"
        private const val COLUMN_FOUNDED = "founded"
        private const val COLUMN_FOUNDER = "founder"
        private const val COLUMN_DIED_OUT = "died_out"

        private const val COLUMN_HOUSE_NAME = "house_name"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_SEAT = "seat"
        private const val COLUMN_WEAPON = "weapon"
        private const val COLUMN_CADET_BRANCH = "cadet_branch"
        private const val COLUMN_SWORN_MEMBER = "sworn_member"
        //end houses region

        //characters region
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_CULTURE = "culture"
        private const val COLUMN_BORN = "born"
        private const val COLUMN_DIED = "died"
        private const val COLUMN_FATHER = "father"
        private const val COLUMN_MOTHER = "mother"
        private const val COLUMN_SPOUSE = "spouse"

        private const val COLUMN_CHARACTER_NAME = "character_name"
        private const val COLUMN_ALIAS = "alias"
        private const val COLUMN_ALLEGIANCE = "allegiance"
        private const val COLUMN_BOOK = "book"
        private const val COLUMN_SERIES = "series"
        private const val COLUMN_PLAYED_BY = "played_by"
        //end characters region

        //houses region
        private const val TABLE_HOUSES_SCRIPT = "create table " +
                TABLE_HOUSES + " (" +
                COLUMN_NAME + " text primary key, " +
                COLUMN_URL + " text not null, " +
                COLUMN_FULL_NAME + " text not null, " +
                COLUMN_REGION + " text not null, " +
                COLUMN_COAT_OF_ARMS + " text not null, " +
                COLUMN_WORDS + " text not null, " +
                COLUMN_CURRENT_LORD + " text not null, " +
                COLUMN_HEIR + " text not null, " +
                COLUMN_OVERLORD + " text not null, " +
                COLUMN_FOUNDED + " text not null, " +
                COLUMN_FOUNDER + " text not null, " +
                COLUMN_DIED_OUT + " text not null)"

        private const val TABLE_HOUSES_TITLES_SCRIPT = "create table " +
                TABLE_HOUSES_TITLES + " (" +
                COLUMN_HOUSE_NAME + " text not null, " +
                COLUMN_TITLE + " text not null)"

        private const val TABLE_HOUSES_SEATS_SCRIPT = "create table " +
                TABLE_HOUSES_SEATS + " (" +
                COLUMN_HOUSE_NAME + " text not null, " +
                COLUMN_SEAT + " text not null)"

        private const val TABLE_HOUSES_CADET_BRANCHES_SCRIPT = "create table " +
                TABLE_HOUSES_CADET_BRANCHES + " (" +
                COLUMN_HOUSE_NAME + " text not null, " +
                COLUMN_CADET_BRANCH + " text not null)"

        private const val TABLE_HOUSES_ANCESTRAL_WEAPONS_SCRIPT = "create table " +
                TABLE_HOUSES_ANCESTRAL_WEAPONS + " (" +
                COLUMN_HOUSE_NAME + " text not null, " +
                COLUMN_WEAPON + " text not null)"

        private const val TABLE_HOUSES_SWORN_MEMBERS_SCRIPT = "create table " +
                TABLE_HOUSES_SWORN_MEMBERS + " (" +
                COLUMN_HOUSE_NAME + " text not null, " +
                COLUMN_SWORN_MEMBER + " text not null)"
        //houses region end

        //characters region
        private const val TABLE_CHARACTERS_SCRIPT = "create table " +
                TABLE_CHARACTERS + " (" +
                COLUMN_NAME + " text not null, " +
                COLUMN_URL + " text not null, " +
                COLUMN_GENDER + " text not null, " +
                COLUMN_CULTURE + " text not null, " +
                COLUMN_BORN + " text not null, " +
                COLUMN_DIED + " text, " +
                COLUMN_FATHER + " text, " +
                COLUMN_MOTHER + " text, " +
                COLUMN_SPOUSE + " text, " +
                COLUMN_HOUSE_NAME + " text)"

        private const val TABLE_CHARACTERS_TITLES_SCRIPT = "create table " +
                TABLE_CHARACTERS_TITLES + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_TITLE + " text not null)"

        private const val TABLE_CHARACTERS_ALIASES_SCRIPT = "create table " +
                TABLE_CHARACTERS_ALIASES + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_ALIAS + " text not null)"

        private const val TABLE_CHARACTERS_ALLEGIANCES_SCRIPT = "create table " +
                TABLE_CHARACTERS_ALLEGIANCES + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_ALLEGIANCE + " text not null)"

        private const val TABLE_CHARACTERS_BOOKS_SCRIPT = "create table " +
                TABLE_CHARACTERS_BOOKS + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_BOOK + " text not null)"

        private const val TABLE_CHARACTERS_POV_BOOKS_SCRIPT = "create table " +
                TABLE_CHARACTERS_POV_BOOKS + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_BOOK + " text not null)"

        private const val TABLE_CHARACTERS_TV_SERIES_SCRIPT = "create table " +
                TABLE_CHARACTERS_TV_SERIES + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_SERIES + " text not null)"

        private const val TABLE_CHARACTERS_PLAYED_BY_SCRIPT = "create table " +
                TABLE_CHARACTERS_PLAYED_BY + " (" +
                COLUMN_CHARACTER_NAME + " text not null, " +
                COLUMN_PLAYED_BY + " text not null)"
        //characters region end

        private var singleton: SQLiteHelper? = null

        operator fun get(context: Context): SQLiteHelper {
            var localInstance: SQLiteHelper? = singleton
            if (localInstance == null) {
                synchronized(SQLiteHelper::class) {
                    localInstance = singleton
                    if (localInstance == null) {
                        singleton = SQLiteHelper(context)
                    }
                }
            }
            return singleton!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let {
            try {
                it.execSQL(TABLE_HOUSES_SCRIPT)
                it.execSQL(TABLE_HOUSES_TITLES_SCRIPT)
                it.execSQL(TABLE_HOUSES_SEATS_SCRIPT)
                it.execSQL(TABLE_HOUSES_CADET_BRANCHES_SCRIPT)
                it.execSQL(TABLE_HOUSES_ANCESTRAL_WEAPONS_SCRIPT)
                it.execSQL(TABLE_HOUSES_SWORN_MEMBERS_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_ALIASES_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_ALLEGIANCES_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_BOOKS_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_TITLES_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_PLAYED_BY_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_POV_BOOKS_SCRIPT)
                it.execSQL(TABLE_CHARACTERS_TV_SERIES_SCRIPT)
            } catch (ex : Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //do nothing
    }

    fun insertCharacter(db : SQLiteDatabase, character: CharacterRes) {
        val characterValues = ContentValues()
        characterValues.put(COLUMN_NAME, character.name)
        characterValues.put(COLUMN_URL, character.url)
        characterValues.put(COLUMN_GENDER, character.gender)
        characterValues.put(COLUMN_CULTURE, character.culture)
        characterValues.put(COLUMN_DIED, character.died)
        characterValues.put(COLUMN_BORN, character.born)
        characterValues.put(COLUMN_FATHER, character.father)
        characterValues.put(COLUMN_MOTHER, character.mother)
        characterValues.put(COLUMN_SPOUSE, character.spouse)
        character.houseId?.let { characterValues.put(COLUMN_HOUSE_NAME, it) }
        db.insert(TABLE_CHARACTERS, null, characterValues)

        if(character.titles.isNotEmpty()) {
            for(title in character.titles) {
                val characterTitle = ContentValues()
                characterTitle.put(COLUMN_CHARACTER_NAME, character.name)
                characterTitle.put(COLUMN_TITLE, title)
                db.insert(TABLE_CHARACTERS_TITLES, null, characterTitle)
            }
        }
        if(character.aliases.isNotEmpty()) {
            for (alias in character.aliases) {
                val characterAlias = ContentValues()
                characterAlias.put(COLUMN_CHARACTER_NAME, character.name)
                characterAlias.put(COLUMN_ALIAS, alias)
                db.insert(TABLE_CHARACTERS_ALIASES, null, characterAlias)
            }
        }
        if(character.allegiances.isNotEmpty()) {
            for (allegiance in character.allegiances) {
                val characterAllegiance = ContentValues()
                characterAllegiance.put(COLUMN_CHARACTER_NAME, character.name)
                characterAllegiance.put(COLUMN_ALLEGIANCE, allegiance)
                db.insert(TABLE_CHARACTERS_ALLEGIANCES, null, characterAllegiance)
            }
        }
        if(character.books.isNotEmpty()) {
            for (book in character.books) {
                val characterBook = ContentValues()
                characterBook.put(COLUMN_CHARACTER_NAME, character.name)
                characterBook.put(COLUMN_BOOK, book)
                db.insert(TABLE_CHARACTERS_BOOKS, null, characterBook)
            }
        }
        if(character.povBooks.isNotEmpty()) {
            for (book in character.povBooks) {
                val characterBook = ContentValues()
                characterBook.put(COLUMN_CHARACTER_NAME, character.name)
                characterBook.put(COLUMN_BOOK, book.toString())
                db.insert(TABLE_CHARACTERS_POV_BOOKS, null, characterBook)
            }
        }
        if(character.tvSeries.isNotEmpty()) {
            for (series in character.tvSeries) {
                val tvSeries = ContentValues()
                tvSeries.put(COLUMN_CHARACTER_NAME, character.name)
                tvSeries.put(COLUMN_SERIES, series)
                db.insert(TABLE_CHARACTERS_TV_SERIES, null, tvSeries)
            }
        }
        if(character.playedBy.isNotEmpty()) {
            for (playedBy in character.playedBy) {
                val characterPlayedBy = ContentValues()
                characterPlayedBy.put(COLUMN_CHARACTER_NAME, character.name)
                characterPlayedBy.put(COLUMN_PLAYED_BY, playedBy)
                db.insert(TABLE_CHARACTERS_PLAYED_BY, null, characterPlayedBy)
            }
        }
    }
}