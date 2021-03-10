package ru.skillbranch.gameofthrones.presenters

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.Tools
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.interactors.MainInteractor
import ru.skillbranch.gameofthrones.repositories.RootRepository
import java.util.concurrent.TimeUnit

class SplashScreenPresenter(private val mainInteractor: MainInteractor) : SplashScreenContract.Presenter {

    private var mView: SplashScreenContract.View? = null

    override fun onLoadingStart(view: SplashScreenContract.View) {
        mView = view

        RootRepository.isNeedUpdate {
            if (it) {
                mainInteractor.getHousesAndCharactersOnline(*AppConfig.HOUSES_LONG_NAMES_LIST.map { it }.toTypedArray(),
                        result = { pairs ->
                            val housesList = ArrayList<HouseRes>()
                            val charactersList = ArrayList<CharacterRes>()

                            for (pair in pairs) {
                                housesList.add(pair.first)
                                for(character in pair.second) {
                                    character.houseId = AppConfig.HOUSES_SHORT_NAMES[pair.first.name]
                                }
                                charactersList.addAll(pair.second)
                            }

                            mainInteractor.saveHousesLocal(*housesList.map { it }.toTypedArray()) {
                                mainInteractor.saveCharactersLocal(*charactersList.map { it }.toTypedArray()) {
                                    mView?.showHousesScreen()
                                }
                            }
                        }, onError = { error ->
                    if (Tools.isOnline()) {
                        error.message?.let { errorMessage ->
                            mView?.showUnknownError(
                                    errorMessage
                            )
                        }
                    } else {
                        mView?.showNoInternetError()
                    }
                })
            } else {
                GlobalScope.launch {
                    delay(TimeUnit.SECONDS.toMillis(5))
                    mView?.showHousesScreen()
                }
            }
        }
    }

    override fun unbindView() {
        mView = null
    }
}