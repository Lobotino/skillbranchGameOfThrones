package ru.skillbranch.gameofthrones.ui

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.transition.*
import com.app.sbgameofthrones.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.remote.GameOfThronesClient
import ru.skillbranch.gameofthrones.interactors.MainInteractor
import ru.skillbranch.gameofthrones.presenters.SplashScreenContract
import ru.skillbranch.gameofthrones.presenters.SplashScreenPresenter
import ru.skillbranch.gameofthrones.repositories.local.LocalCharactersRepository
import ru.skillbranch.gameofthrones.repositories.local.LocalHousesRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineCharactersRepository
import ru.skillbranch.gameofthrones.repositories.remote.OnlineHousesRepository
import java.util.concurrent.TimeUnit


class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    private var containerView : ViewGroup? = null

    private var animationJob : Job? = null

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

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_primary)
    }

    override fun showHousesScreen() {
        animationJob?.cancel()
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