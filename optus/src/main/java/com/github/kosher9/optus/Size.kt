package com.github.kosher9.optus

/**
 * Immutable class for describing width and height dimension in pixels
 *
 * @param width The width of the size, in pixels
 * @param height The height of the size, in pixels
 */
class Size(private val width: Int, private val height: Int) : Comparable<Size> {

    override fun equals(other: Any?): Boolean {
        if (other == null){
            return false
        }
        if (this === other){
            return true
        }
        if (other is Size){
            val size: Size? = other
            return width == size!!.width && height == size!!.height
        }
        return false
    }

    override fun toString(): String {
        return "$width"+"x"+"$height"
    }

    override fun hashCode(): Int {
        return height xor (width shl Integer.SIZE / 2 or width.ushr(Integer.SIZE / 2))
    }

    override fun compareTo(other: Size): Int {
        return width * height - other.width * other.height
    }
}