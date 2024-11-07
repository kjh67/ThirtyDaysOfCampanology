package uk.ac.cam.kjh67.thirtydaysofcampanology.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CampanologyTip(
    val tipNumber: Int,
    @StringRes val tipTitle: Int,
    @DrawableRes val tipImage: Int,
    @StringRes val tipContent: Int
)
