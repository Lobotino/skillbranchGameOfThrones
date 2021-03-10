package ru.skillbranch.gameofthrones.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import java.lang.Exception

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "GameOfThrones"
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

        private const val COLUMN_ID = "id"

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
                COLUMN_REGION + " text, " +
                COLUMN_COAT_OF_ARMS + " text, " +
                COLUMN_WORDS + " text, " +
                COLUMN_CURRENT_LORD + " text, " +
                COLUMN_HEIR + " text, " +
                COLUMN_OVERLORD + " text, " +
                COLUMN_FOUNDED + " text, " +
                COLUMN_FOUNDER + " text, " +
                COLUMN_DIED_OUT + " text)"

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
                COLUMN_ID + " integer primary key, " +
                COLUMN_NAME + " text not null, " +
                COLUMN_URL + " text not null, " +
                COLUMN_GENDER + " text, " +
                COLUMN_CULTURE + " text, " +
                COLUMN_BORN + " text, " +
                COLUMN_DIED + " text, " +
                COLUMN_FATHER + " text, " +
                COLUMN_MOTHER + " text, " +
                COLUMN_SPOUSE + " text, " +
                COLUMN_HOUSE_NAME + " text)"

        private const val TABLE_CHARACTERS_TITLES_SCRIPT = "create table " +
                TABLE_CHARACTERS_TITLES + " (" +
                COLUMN_ID + " integer not null, " +
                COLUMN_TITLE + " text not null)"

        private const val TABLE_CHARACTERS_ALIASES_SCRIPT = "create table " +
                TABLE_CHARACTERS_ALIASES + " (" +
                COLUMN_ID + " integer not null, " +
                COLUMN_ALIAS + " text not null)"

        private const val TABLE_CHARACTERS_ALLEGIANCES_SCRIPT = "create table " +
                TABLE_CHARACTERS_ALLEGIANCES + " (" +
                COLUMN_ID + " integer not null, " +
                COLUMN_ALLEGIANCE + " text not null)"

        private const val TABLE_CHARACTERS_BOOKS_SCRIPT = "create table " +
                TABLE_CHARACTERS_BOOKS + " (" +
                COLUMN_ID + " integer not null, " +
                COLUMN_BOOK + " text not null)"

        private const val TABLE_CHARACTERS_POV_BOOKS_SCRIPT = "create table " +
                TABLE_CHARACTERS_POV_BOOKS + " (" +
                COLUMN_ID + " integer not null, " +
                COLUMN_BOOK + " text not null)"

        private const val TABLE_CHARACTERS_TV_SERIES_SCRIPT = "create table " +
                TABLE_CHARACTERS_TV_SERIES + " (" +
                COLUMN_ID + " integer not null, " +
                COLUMN_SERIES + " text not null)"

        private const val TABLE_CHARACTERS_PLAYED_BY_SCRIPT = "create table " +
                TABLE_CHARACTERS_PLAYED_BY + " (" +
                COLUMN_ID + " integer not null, " +
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
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //do nothing
    }

    fun insertHouse(db: SQLiteDatabase, houseRes: HouseRes) {
        val houseShortName = AppConfig.HOUSES_SHORT_NAMES[houseRes.name]

        val houseValues = ContentValues()
        houseValues.put(COLUMN_URL, houseRes.url)
        houseValues.put(COLUMN_NAME, houseShortName)
        houseValues.put(COLUMN_FULL_NAME, houseRes.name)
        houseValues.put(COLUMN_REGION, houseRes.region)
        houseValues.put(COLUMN_COAT_OF_ARMS, houseRes.coatOfArms)
        houseValues.put(COLUMN_WORDS, houseRes.words)
        houseValues.put(COLUMN_CURRENT_LORD, houseRes.currentLord)
        houseValues.put(COLUMN_HEIR, houseRes.heir)
        houseValues.put(COLUMN_OVERLORD, houseRes.overlord)
        houseValues.put(COLUMN_FOUNDED, houseRes.founded)
        houseValues.put(COLUMN_FOUNDER, houseRes.founder)
        houseValues.put(COLUMN_DIED_OUT, houseRes.diedOut)
        db.insert(TABLE_HOUSES, null, houseValues)

        if (houseRes.titles.isNotEmpty()) {
            for (title in houseRes.titles) {
                val houseTitle = ContentValues()
                houseTitle.put(COLUMN_HOUSE_NAME, houseRes.name)
                houseTitle.put(COLUMN_TITLE, title)
                db.insert(TABLE_HOUSES_TITLES, null, houseTitle)
            }
        }
        if (houseRes.seats.isNotEmpty()) {
            for (seat in houseRes.seats) {
                val houseSeat = ContentValues()
                houseSeat.put(COLUMN_HOUSE_NAME, houseRes.name)
                houseSeat.put(COLUMN_SEAT, seat)
                db.insert(TABLE_HOUSES_SEATS, null, houseSeat)
            }
        }
        if (houseRes.ancestralWeapons.isNotEmpty()) {
            for (weapon in houseRes.ancestralWeapons) {
                if(weapon.isNotEmpty()) {
                    val houseWeapon = ContentValues()
                    houseWeapon.put(COLUMN_HOUSE_NAME, houseRes.name)
                    houseWeapon.put(COLUMN_WEAPON, weapon)
                    db.insert(TABLE_HOUSES_ANCESTRAL_WEAPONS, null, houseWeapon)
                }
            }
        }
        if (houseRes.cadetBranches.isNotEmpty()) {
            for (cadetBranch in houseRes.cadetBranches) {
                if(cadetBranch.toString().isNotEmpty()) {
                    val cadetBranchValues = ContentValues()
                    cadetBranchValues.put(COLUMN_HOUSE_NAME, houseRes.name)
                    cadetBranchValues.put(COLUMN_CADET_BRANCH, cadetBranch.toString())
                    db.insert(TABLE_HOUSES_CADET_BRANCHES, null, cadetBranchValues)
                }
            }
        }
        if (houseRes.swornMembers.isNotEmpty()) {
            for (swornMember in houseRes.swornMembers) {
                if(swornMember.isNotEmpty()) {
                    val swornMembersValues = ContentValues()
                    swornMembersValues.put(COLUMN_HOUSE_NAME, houseRes.name)
                    swornMembersValues.put(COLUMN_SWORN_MEMBER, swornMember)
                    db.insert(TABLE_HOUSES_SWORN_MEMBERS, null, swornMembersValues)
                }
            }
        }
    }

    fun getHouseResByShortName(db: SQLiteDatabase, shortName: String): HouseRes {
        var url = ""
        var fullName = ""
        var region = ""
        var coatOfArms = ""
        var words = ""
        var currentLord = ""
        var heir = ""
        var overlord = ""
        var founded = ""
        var founder = ""
        var diedOut = ""

        val houseCursor = db.rawQuery("SELECT * FROM $TABLE_HOUSES WHERE $COLUMN_NAME LIKE '$shortName'", null)
        if (houseCursor.moveToFirst()) {
            url = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_URL))
            fullName = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_FULL_NAME))
            region = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_REGION))
            coatOfArms = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_COAT_OF_ARMS))
            words = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_WORDS))
            currentLord = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_CURRENT_LORD))
            heir = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_HEIR))
            overlord = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_OVERLORD))
            founded = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_FOUNDED))
            founder = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_FOUNDER))
            diedOut = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_DIED_OUT))
        }
        houseCursor.close()

        val titlesList = ArrayList<String>()
        val titlesCursor = db.rawQuery("SELECT $COLUMN_TITLE FROM $TABLE_HOUSES_TITLES WHERE $COLUMN_HOUSE_NAME LIKE '$shortName'", null)
        if (titlesCursor.moveToFirst()) {
            do {
                titlesList.add(titlesCursor.getString(titlesCursor.getColumnIndex(COLUMN_TITLE)))
            } while (titlesCursor.moveToNext())
        }
        titlesCursor.close()

        val seatsList = ArrayList<String>()
        val seatsCursor = db.rawQuery("SELECT $COLUMN_SEAT FROM $TABLE_HOUSES_SEATS WHERE $COLUMN_HOUSE_NAME LIKE '$shortName'", null)
        if (seatsCursor.moveToFirst()) {
            do {
                seatsList.add(seatsCursor.getString(seatsCursor.getColumnIndex(COLUMN_SEAT)))
            } while (seatsCursor.moveToNext())
        }
        seatsCursor.close()

        val ancestralWeaponsList = ArrayList<String>()
        val ancestralWeaponsCursor = db.rawQuery("SELECT $COLUMN_WEAPON FROM $TABLE_HOUSES_ANCESTRAL_WEAPONS WHERE $COLUMN_HOUSE_NAME LIKE '$shortName'", null)
        if (ancestralWeaponsCursor.moveToFirst()) {
            do {
                ancestralWeaponsList.add(ancestralWeaponsCursor.getString(ancestralWeaponsCursor.getColumnIndex(COLUMN_WEAPON)))
            } while (ancestralWeaponsCursor.moveToNext())
        }
        ancestralWeaponsCursor.close()

        val cadetBranchesList = ArrayList<String>()
        val cadetBranchesCursor = db.rawQuery("SELECT $COLUMN_CADET_BRANCH FROM $TABLE_HOUSES_CADET_BRANCHES WHERE $COLUMN_HOUSE_NAME LIKE '$shortName'", null)
        if (cadetBranchesCursor.moveToFirst()) {
            do {
                cadetBranchesList.add(cadetBranchesCursor.getString(cadetBranchesCursor.getColumnIndex(COLUMN_CADET_BRANCH)))
            } while (cadetBranchesCursor.moveToNext())
        }
        cadetBranchesCursor.close()

        val swornMembersList = ArrayList<String>()
        val swornMembersCursor = db.rawQuery("SELECT $COLUMN_SWORN_MEMBER FROM $TABLE_HOUSES_SWORN_MEMBERS WHERE $COLUMN_HOUSE_NAME LIKE '$shortName'", null)
        if (swornMembersCursor.moveToFirst()) {
            do {
                swornMembersList.add(swornMembersCursor.getString(swornMembersCursor.getColumnIndex(COLUMN_SWORN_MEMBER)))
            } while (swornMembersCursor.moveToNext())
        }
        swornMembersCursor.close()

        return HouseRes(
                url = url,
                name = fullName,
                region = region,
                coatOfArms = coatOfArms,
                words = words,
                titles = titlesList,
                seats = seatsList,
                currentLord = currentLord,
                heir = heir,
                overlord = overlord,
                founded = founded,
                founder = founder,
                diedOut = diedOut,
                ancestralWeapons = ancestralWeaponsList,
                cadetBranches = cadetBranchesList,
                swornMembers = swornMembersList
        )
    }

    fun insertCharacter(db: SQLiteDatabase, character: CharacterRes, parseParentIdFromUrl: (String) -> Long) {
        val characterId = parseParentIdFromUrl(character.url)

        val characterValues = ContentValues()
        characterValues.put(COLUMN_ID, characterId)
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

        if (character.titles.isNotEmpty()) {
            for (title in character.titles) {
                if (title.isNotEmpty()) {
                    val characterTitle = ContentValues()
                    characterTitle.put(COLUMN_ID, characterId)
                    characterTitle.put(COLUMN_TITLE, title)
                    db.insert(TABLE_CHARACTERS_TITLES, null, characterTitle)
                }
            }
        }
        if (character.aliases.isNotEmpty()) {
            for (alias in character.aliases) {
                if (alias.isNotEmpty()) {
                    val characterAlias = ContentValues()
                    characterAlias.put(COLUMN_ID, characterId)
                    characterAlias.put(COLUMN_ALIAS, alias)
                    db.insert(TABLE_CHARACTERS_ALIASES, null, characterAlias)
                }
            }
        }
        if (character.allegiances.isNotEmpty()) {
            for (allegiance in character.allegiances) {
                if (allegiance.isNotEmpty()) {
                    val characterAllegiance = ContentValues()
                    characterAllegiance.put(COLUMN_ID, characterId)
                    characterAllegiance.put(COLUMN_ALLEGIANCE, allegiance)
                    db.insert(TABLE_CHARACTERS_ALLEGIANCES, null, characterAllegiance)
                }
            }
        }
        if (character.books.isNotEmpty()) {
            for (book in character.books) {
                if (book.isNotEmpty()) {
                    val characterBook = ContentValues()
                    characterBook.put(COLUMN_ID, characterId)
                    characterBook.put(COLUMN_BOOK, book)
                    db.insert(TABLE_CHARACTERS_BOOKS, null, characterBook)
                }
            }
        }
        if (character.povBooks.isNotEmpty()) {
            for (book in character.povBooks) {
                if (book.toString().isNotEmpty()) {
                    val characterBook = ContentValues()
                    characterBook.put(COLUMN_ID, characterId)
                    characterBook.put(COLUMN_BOOK, book.toString())
                    db.insert(TABLE_CHARACTERS_POV_BOOKS, null, characterBook)
                }
            }
        }
        if (character.tvSeries.isNotEmpty()) {
            for (series in character.tvSeries) {
                if (series.isNotEmpty()) {
                    val tvSeries = ContentValues()
                    tvSeries.put(COLUMN_ID, characterId)
                    tvSeries.put(COLUMN_SERIES, series)
                    db.insert(TABLE_CHARACTERS_TV_SERIES, null, tvSeries)
                }
            }
        }
        if (character.playedBy.isNotEmpty()) {
            for (playedBy in character.playedBy) {
                if (playedBy.isNotEmpty()) {
                    val characterPlayedBy = ContentValues()
                    characterPlayedBy.put(COLUMN_ID, characterId)
                    characterPlayedBy.put(COLUMN_PLAYED_BY, playedBy)
                    db.insert(TABLE_CHARACTERS_PLAYED_BY, null, characterPlayedBy)
                }
            }
        }
    }

    fun getCharacterById(db: SQLiteDatabase, id: Long): CharacterRes {
        var name = ""
        var gender = ""
        var culture = ""
        var born = ""
        var died = ""
        var url = ""
        var father = ""
        var mother = ""
        var spouse = ""
        var house: String? = null

        val characterCursor = db.rawQuery("SELECT * FROM $TABLE_CHARACTERS WHERE $COLUMN_ID = $id", null)
        if (characterCursor.moveToFirst() && characterCursor.count > 0) {
            name = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_NAME))
            url = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_URL))
            gender = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_GENDER))
            culture = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_CULTURE))
            born = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_BORN))
            died = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_DIED))
            house = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_HOUSE_NAME))
            father = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_FATHER))
            mother = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_MOTHER))
            spouse = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_SPOUSE))
        }
        characterCursor.close()

        val titlesList = ArrayList<String>()
        val titlesCursor = db.rawQuery("SELECT $COLUMN_TITLE FROM $TABLE_CHARACTERS_TITLES WHERE $COLUMN_ID = $id", null)
        if (titlesCursor.moveToFirst()) {
            do {
                titlesList.add(titlesCursor.getString(titlesCursor.getColumnIndex(COLUMN_TITLE)))
            } while (titlesCursor.moveToNext())
        }
        titlesCursor.close()

        val aliasesList = ArrayList<String>()
        val aliasesCursor = db.rawQuery("SELECT $COLUMN_ALIAS FROM $TABLE_CHARACTERS_ALIASES WHERE $COLUMN_ID = $id", null)
        if (aliasesCursor.moveToFirst()) {
            do {
                aliasesList.add(aliasesCursor.getString(aliasesCursor.getColumnIndex(COLUMN_ALIAS)))
            } while (aliasesCursor.moveToNext())
        }
        aliasesCursor.close()

        val allegiancesList = ArrayList<String>()
        val allegiancesCursor = db.rawQuery("SELECT $COLUMN_ALLEGIANCE FROM $TABLE_CHARACTERS_ALLEGIANCES WHERE $COLUMN_ID = $id", null)
        if (allegiancesCursor.moveToFirst()) {
            do {
                allegiancesList.add(allegiancesCursor.getString(allegiancesCursor.getColumnIndex(COLUMN_ALLEGIANCE)))
            } while (allegiancesCursor.moveToNext())
        }
        allegiancesCursor.close()

        val booksList = ArrayList<String>()
        val booksCursor = db.rawQuery("SELECT $COLUMN_BOOK FROM $TABLE_CHARACTERS_BOOKS WHERE $COLUMN_ID = $id", null)
        if (booksCursor.moveToFirst()) {
            do {
                booksList.add(booksCursor.getString(booksCursor.getColumnIndex(COLUMN_BOOK)))
            } while (booksCursor.moveToNext())
        }
        booksCursor.close()

        val povBooksList = ArrayList<String>()
        val povBooksCursor = db.rawQuery("SELECT $COLUMN_BOOK FROM $TABLE_CHARACTERS_POV_BOOKS WHERE $COLUMN_ID = $id", null)
        if (povBooksCursor.moveToFirst()) {
            do {
                povBooksList.add(povBooksCursor.getString(povBooksCursor.getColumnIndex(COLUMN_BOOK)))
            } while (povBooksCursor.moveToNext())
        }
        povBooksCursor.close()

        val tvSeriesList = ArrayList<String>()
        val tvSeriesCursor = db.rawQuery("SELECT $COLUMN_SERIES FROM $TABLE_CHARACTERS_TV_SERIES WHERE $COLUMN_ID = $id", null)
        if (tvSeriesCursor.moveToFirst()) {
            do {
                tvSeriesList.add(tvSeriesCursor.getString(tvSeriesCursor.getColumnIndex(COLUMN_SERIES)))
            } while (tvSeriesCursor.moveToNext())
        }
        tvSeriesCursor.close()

        val playerByList = ArrayList<String>()
        val playedByCursor = db.rawQuery("SELECT $COLUMN_PLAYED_BY FROM $TABLE_CHARACTERS_PLAYED_BY WHERE $COLUMN_ID = $id", null)
        if (playedByCursor.moveToFirst()) {
            do {
                playerByList.add(playedByCursor.getString(playedByCursor.getColumnIndex(COLUMN_PLAYED_BY)))
            } while (playedByCursor.moveToNext())
        }
        playedByCursor.close()

        return CharacterRes(
                url = url,
                name = name,
                gender = gender,
                culture = culture,
                born = born,
                died = died,
                father = father,
                mother = mother,
                spouse = spouse,
                houseId = house,
                titles = titlesList,
                aliases = aliasesList,
                allegiances = allegiancesList,
                books = booksList,
                povBooks = povBooksList,
                tvSeries = tvSeriesList,
                playedBy = playerByList)
    }

    fun getCharacterFullById(db: SQLiteDatabase, id : String, parseParentIdFROMUrl: (String) -> Long) : CharacterFull {
        var name = ""
        var born = ""
        var died = ""
        var fatherUrl = ""
        var motherUrl = ""
        var words = ""
        var shortHouseName = ""

        val characterCursor = db.rawQuery("SELECT $COLUMN_NAME, $COLUMN_HOUSE_NAME, $COLUMN_BORN, $COLUMN_DIED, $COLUMN_FATHER, $COLUMN_MOTHER " +
                "FROM $TABLE_CHARACTERS WHERE $COLUMN_ID = $id ", null)
        if (characterCursor.moveToFirst()) {
            name = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_NAME))
            born = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_BORN))
            died = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_DIED))
            shortHouseName = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_HOUSE_NAME))
            fatherUrl = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_FATHER))
            motherUrl = characterCursor.getString(characterCursor.getColumnIndex(COLUMN_MOTHER))
        }
        characterCursor.close()

        val houseCursor = db.rawQuery("SELECT $COLUMN_WORDS FROM $TABLE_HOUSES WHERE $COLUMN_NAME LIKE '$shortHouseName'", null)
        if (houseCursor.moveToFirst()) {
            words = houseCursor.getString(houseCursor.getColumnIndex(COLUMN_WORDS))
        }
        houseCursor.close()

        val titlesList = ArrayList<String>()
        val titlesCursor = db.rawQuery("SELECT $COLUMN_TITLE FROM $TABLE_CHARACTERS_TITLES WHERE $COLUMN_ID = $id", null)
        if (titlesCursor.moveToFirst()) {
            do {
                titlesList.add(titlesCursor.getString(titlesCursor.getColumnIndex(COLUMN_TITLE)))
            } while (titlesCursor.moveToNext())
        }
        titlesCursor.close()

        val aliasesList = ArrayList<String>()
        val aliasesCursor = db.rawQuery("SELECT $COLUMN_ALIAS FROM $TABLE_CHARACTERS_ALIASES WHERE $COLUMN_ID = $id", null)
        if (aliasesCursor.moveToFirst()) {
            do {
                aliasesList.add(aliasesCursor.getString(aliasesCursor.getColumnIndex(COLUMN_ALIAS)))
            } while (aliasesCursor.moveToNext())
        }
        aliasesCursor.close()

        return CharacterFull(
                id = id,
                name = name,
                words = words,
                born = born,
                died = died,
                titles = titlesList,
                aliases = aliasesList,
                house = shortHouseName,
                father = getParent(db, parseParentIdFROMUrl(fatherUrl)),
                mother = getParent(db, parseParentIdFROMUrl(motherUrl))
        )
    }

    private fun getParent(db : SQLiteDatabase, id : Long) : RelativeCharacter? {
        val parentCursor = db.rawQuery("SELECT $COLUMN_NAME, $COLUMN_HOUSE_NAME FROM $TABLE_CHARACTERS WHERE $COLUMN_ID = $id", null)
        return if (parentCursor.moveToFirst()) {
            val result = RelativeCharacter(
                    id = id.toString(),
                    name = parentCursor.getString(parentCursor.getColumnIndex(COLUMN_NAME)),
                    house = parentCursor.getString(parentCursor.getColumnIndex(COLUMN_HOUSE_NAME))
            )
            parentCursor.close()
            result
        } else {
            parentCursor.close()
            null
        }
    }

    fun getCharactersItemsForHouse(db : SQLiteDatabase, shortHouseName : String) : List<CharacterItem> {
        val resultList = ArrayList<CharacterItem>()

        val charactersCursor = db.rawQuery("SELECT $COLUMN_ID, $COLUMN_NAME FROM $TABLE_CHARACTERS WHERE $COLUMN_HOUSE_NAME LIKE '$shortHouseName'", null)
        if (charactersCursor.moveToFirst()) {
            do {
                val id = charactersCursor.getString(charactersCursor.getColumnIndex(COLUMN_ID))
                val name = charactersCursor.getString(charactersCursor.getColumnIndex(COLUMN_NAME))

                val titlesList = ArrayList<String>()
                val titlesCursor = db.rawQuery("SELECT $COLUMN_TITLE FROM $TABLE_CHARACTERS_TITLES WHERE $COLUMN_ID = $id", null)
                if (titlesCursor.moveToFirst()) {
                    do {
                        titlesList.add(titlesCursor.getString(titlesCursor.getColumnIndex(COLUMN_TITLE)))
                    } while (titlesCursor.moveToNext())
                }
                titlesCursor.close()

                val aliasesList = ArrayList<String>()
                val aliasesCursor = db.rawQuery("SELECT $COLUMN_ALIAS FROM $TABLE_CHARACTERS_ALIASES WHERE $COLUMN_ID = $id", null)
                if (aliasesCursor.moveToFirst()) {
                    do {
                        aliasesList.add(aliasesCursor.getString(aliasesCursor.getColumnIndex(COLUMN_ALIAS)))
                    } while (aliasesCursor.moveToNext())
                }
                aliasesCursor.close()

                resultList.add(CharacterItem(
                        id = id,
                        name = name,
                        house = shortHouseName,
                        titles = titlesList,
                        aliases = aliasesList
                ))
            } while (charactersCursor.moveToNext())
        }
        charactersCursor.close()

        return resultList
    }

    fun isEmptyDb(db: SQLiteDatabase, onComplete: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val allTables = listOf(TABLE_CHARACTERS,
                    TABLE_CHARACTERS_PLAYED_BY,
                    TABLE_CHARACTERS_TV_SERIES,
                    TABLE_CHARACTERS_BOOKS,
                    TABLE_CHARACTERS_ALLEGIANCES,
                    TABLE_CHARACTERS_ALIASES,
                    TABLE_CHARACTERS_TITLES,
                    TABLE_HOUSES,
                    TABLE_HOUSES_SWORN_MEMBERS,
                    TABLE_HOUSES_CADET_BRANCHES,
                    TABLE_HOUSES_ANCESTRAL_WEAPONS,
                    TABLE_HOUSES_SEATS,
                    TABLE_HOUSES_TITLES)

            for(table in allTables) {
                val cursor = db.rawQuery("SELECT count(*) FROM $table", null)
                cursor.moveToFirst()
                val isEmpty = cursor.getInt(0) == 0
                cursor.close()

                if (!isEmpty) {
                    onComplete(false)
                    return@launch
                }
            }

            onComplete(true)
        }
    }

    fun dropDb(db : SQLiteDatabase, onComplete: () -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val allTables = listOf(TABLE_CHARACTERS,
                    TABLE_CHARACTERS_PLAYED_BY,
                    TABLE_CHARACTERS_TV_SERIES,
                    TABLE_CHARACTERS_BOOKS,
                    TABLE_CHARACTERS_ALLEGIANCES,
                    TABLE_CHARACTERS_ALIASES,
                    TABLE_CHARACTERS_TITLES,
                    TABLE_HOUSES,
                    TABLE_HOUSES_SWORN_MEMBERS,
                    TABLE_HOUSES_CADET_BRANCHES,
                    TABLE_HOUSES_ANCESTRAL_WEAPONS,
                    TABLE_HOUSES_SEATS,
                    TABLE_HOUSES_TITLES)
            for(table in allTables) {
                db.execSQL( "DELETE FROM $table")
            }
            onComplete()
        }
    }
}