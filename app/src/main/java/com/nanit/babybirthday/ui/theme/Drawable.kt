package com.nanit.babybirthday.ui.theme

import com.nanit.babybirthday.R

enum class AgeDrawableResource(val age: Int, val drawable: Int) {
    ZERO(0, R.drawable.age_0),
    ONE(1, R.drawable.age_1),
    TWO(2, R.drawable.age_2),
    THREE(3, R.drawable.age_3),
    FOUR(4, R.drawable.age_4),
    FIVE(5, R.drawable.age_5),
    SIX(6, R.drawable.age_6),
    SEVEN(7, R.drawable.age_7),
    EIGHT(8, R.drawable.age_8),
    NINE(9, R.drawable.age_9),
    TEN(10, R.drawable.age_10),
    ELEVEN(11, R.drawable.age_11),
    TWELVE(12, R.drawable.age_12);

    companion object {
        fun getDrawable(age: Int) =
            AgeDrawableResource.entries.firstOrNull { it.age == age } ?: run {
                TWELVE
            }
    }
}