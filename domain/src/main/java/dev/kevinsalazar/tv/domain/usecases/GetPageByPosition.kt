package dev.kevinsalazar.tv.domain.usecases

import dev.kevinsalazar.tv.domain.constants.PHOTOS_PER_PAGE
import javax.inject.Inject
import kotlin.math.ceil

class GetPageByPosition @Inject constructor() {

    operator fun invoke(position: Int): Int {
        val page = position / PHOTOS_PER_PAGE.toDouble()
        return ceil(page).toInt()
    }
}