package ru.skillbranch.gameofthrones.ui

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.transition.Transition
import androidx.transition.TransitionValues
import android.view.ViewGroup

class ChangeColor : Transition() {

    companion object {
        const val PROPNAME_BACKGROUND = "customtransition:change_color:background"
    }

    private fun captureValues(values: TransitionValues) {
        values.values[PROPNAME_BACKGROUND] = values.view.background;
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null) {
            return null
        }

        val view = endValues.view

        val startBackground = startValues.values[PROPNAME_BACKGROUND] as Drawable
        val endBackground = endValues.values[PROPNAME_BACKGROUND] as Drawable

        val startColor = startBackground as ColorDrawable
        val endColor = endBackground as ColorDrawable

        if (startColor.color == endColor.color) {
            return null
        }

        val animator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            startColor.color, endColor.color
        );
        animator.addUpdateListener { animation ->
            val value = animation.getAnimatedValue()
            if (value != null) {
                view.setBackgroundColor(value as Int)
            }
        }
        return animator
    }
}