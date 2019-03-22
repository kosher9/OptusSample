package com.github.kosher9.optus

internal interface Constants {
    companion object {

        val DEFAULT_ASPECT_RATIO = AspectRatio.of(4, 3)

        val FACING_BACK: Int = 0
        val FACING_FRONT: Int = 1

        val FLASH_OFF: Int = 0
        val FLASH_ON: Int = 1
        val FLASH_TORCH: Int = 2
        val FLASH_AUTO: Int = 3
        val FLASH_RED_EYE: Int = 4

        val LANDSCAPE_90: Int = 90
        val LANDSCAPE_270: Int = 270
    }
}