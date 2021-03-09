package ru.skillbranch.gameofthrones.presenters

import ru.skillbranch.gameofthrones.Tools
import ru.skillbranch.gameofthrones.interactors.MainInteractor
import ru.skillbranch.gameofthrones.repositories.RootRepository

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
                mView?.showHousesScreen()
            }
        }
    }

    override fun unbindView() {
        mView = null
    }
}