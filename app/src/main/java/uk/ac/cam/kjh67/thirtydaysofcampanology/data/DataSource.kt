package uk.ac.cam.kjh67.thirtydaysofcampanology.data

import uk.ac.cam.kjh67.thirtydaysofcampanology.model.CampanologyTip
import uk.ac.cam.kjh67.thirtydaysofcampanology.R

object DataSource {
    val campanologyTips = listOf<CampanologyTip>(
        CampanologyTip(1, R.string.tip1Title, R.drawable.pub, R.string.tip1Content),
        CampanologyTip(2, R.string.tip2Title, R.drawable.pub, R.string.tip2Content),
        CampanologyTip(3, R.string.tip3Title, R.drawable.pub, R.string.tip3Content),
        CampanologyTip(4, R.string.tip4Title, R.drawable.pub, R.string.tip4Content),
        CampanologyTip(5, R.string.tip5Title, R.drawable.pub, R.string.tip5Content)
    )
}