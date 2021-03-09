package ru.skillbranch.gameofthrones.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.sbgameofthrones.R
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesClient
import ru.skillbranch.gameofthrones.interactors.MainInteractor
import ru.skillbranch.gameofthrones.presenters.SplashScreenContract
import ru.skillbranch.gameofthrones.presenters.SplashScreenPresenter
import ru.skillbranch.gameofthrones.repositories.local.LocalCharactersRepository
import ru.skillbranch.gameofthrones.repositories.local.LocalHousesRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineCharactersRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineHousesRepository

class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    private var containerView : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        containerView = findViewById(R.id.container)

        val mApi = GameOfThronesClient().getApiClient()
        val mPresenter = SplashScreenPresenter(
            MainInteractor(
                OnlineHousesRepository(mApi),
                OnlineCharactersRepository(mApi),
                LocalHousesRepository(App.app!!.getContext()),
                LocalCharactersRepository(App.app!!.getContext())
            )
        )
        mPresenter.onLoadingStart(this)
        Snackbar.make(containerView!!, "Start loading!", Snackbar.LENGTH_SHORT).show()
    }

    override fun showHousesScreen() {
        val intent = Intent(this, HousesListActivity::class.java)
        startActivity(intent)
    }

    override fun showNoInternetError() {
        Snackbar.make(containerView!!, "No internet connection!", Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun showUnknownError(message: String) {
        Snackbar.make(containerView!!, "Unknown error: $message", Snackbar.LENGTH_INDEFINITE).show()
    }
}