package ru.skillbranch.gameofthrones.presenters

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.Tools
import ru.skillbranch.gameofthrones.interactors.MainInteractor
import ru.skillbranch.gameofthrones.repositories.RootRepository
import java.util.concurrent.TimeUnit

class SplashScreenPresenter(private val mainInteractor: MainInteractor) : SplashScreenContract.Presenter {

    private var mView: SplashScreenContract.View? = null

    override fun onLoadingStart(view: SplashScreenContract.View) {
        mView = view

        RootRepository.isNeedUpdate {
            if (it) {
                mainInteractor.getAllHousesOnline(
                    result = { housesList ->
                        mainInteractor.saveHousesLocal(houses = housesList.map { it }.toTypedArray()) {
                            mView?.showHousesScreen()
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