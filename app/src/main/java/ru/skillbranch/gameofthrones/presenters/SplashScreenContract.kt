package ru.skillbranch.gameofthrones.presenters

interface SplashScreenContract {

    interface Presenter {
        fun onLoadingStart(view : View)

        fun unbindView()
    }

    interface View {
        fun showHousesScreen()

        fun showNoInternetError()

        fun showUnknownError(message : String)
    }
}