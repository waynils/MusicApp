package com.waynils.exoapplication.helper.databinding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.waynils.exoapplication.Constants
import com.waynils.exoapplication.R
import com.waynils.exoapplication.helper.extension.getColor
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerControlView


@BindingAdapter("app:player")
fun StyledPlayerControlView.givePlayer(player: SimpleExoPlayer?) {
    this.player = player
}

@BindingAdapter("app:idImage")
fun ImageView.loadImage(idImage: String?) {
    if (idImage == null)
        setBackgroundColor(getColor(R.color.placeholder_color))
    else {
        Glide.with(this)
                .load(Constants.PROD_URL_IMAGES.plus(idImage))
                .into(this)
    }
}

@BindingAdapter("android:visible")
fun View.changeVisible(visible: Boolean) {
    if (isVisible != visible) isVisible = visible
}

@BindingAdapter("android:invisible")
fun View.changeInvisible(invisible: Boolean) {
    if (isInvisible != invisible) isInvisible = invisible
}

@BindingAdapter("android:enabled")
fun View.changeEnabled(enabled: Boolean) {
    if (isEnabled != enabled) {
        isEnabled = enabled
    }
}




