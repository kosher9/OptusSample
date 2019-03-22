package com.github.kosher9.optus

import android.os.Parcel
import android.os.Parcelable
import android.support.v4.util.SparseArrayCompat
import android.util.Size

/**
 *Immutable class décrivant une relation proportionnelle entre width et height
 */
class AspectRatio(private var mX: Int, private var mY: Int) : Comparable<AspectRatio>, Parcelable {


    /*private var mX: Int = 0
    private var mY: Int = 0*/

    /*constructor(parcel: Parcel) : this(
        mX = parcel.readInt(),
        mY = parcel.readInt()
    )*/

    /*init{
        this.mX = mX
        this.mY = mY
    }*/


    fun matches(size: Size): Boolean {
        val gcd = gcd(size.width, size.height)
        val x = size.width / gcd
        val y = size.height / gcd
        return mX == x && mY == y
    }

    override fun equals(o: Any?): Boolean {
        if (o == null) {
            return false
        }
        if (this === o) {
            return true
        }
        if (o is AspectRatio) {
            val ratio: AspectRatio? = o
            return mX == ratio!!.mX && mY == ratio.mY
        }
        return false
    }

    override fun toString(): String {
        return "$mX:$mY"
    }

    fun toFloat(): Float {
        return mX.toFloat() / mY
    }

    override fun hashCode(): Int {
        // assuming most sizes are <2^16, doing a rotate will give us perfect hashing
        return mY xor (mX shl Integer.SIZE / 2 or mX.ushr(Integer.SIZE / 2))
    }

    override fun compareTo(other: AspectRatio): Int {
        if (equals(other)) {
            return 0
        } else if (toFloat() - other.toFloat() > 0) {
            return 1
        }
        return -1
    }


    /**
     * @return the inverse of [AspectRatio]
     */
    fun inverse(): AspectRatio {
        return AspectRatio.of(mY, mX)
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mX)
        parcel.writeInt(mY)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AspectRatio> {
        private val sCache = SparseArrayCompat<SparseArrayCompat<AspectRatio>>(16)

        private fun gcd(a: Int, b: Int): Int {
            var a = a
            var b = b
            while (b != 0) {
                val c = b
                b = a % b
                a = c
            }
            return a
        }

        fun parse(s: String): AspectRatio {
            val position = s.indexOf(":")
            if (position == -1) {
                throw IllegalArgumentException("Malformed aspect ratio: $s")
            }
            try {
                val x = Integer.parseInt(s.substring(0, position))
                val y = Integer.parseInt(s.substring(position + 1))
                return AspectRatio.of(x, y)
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Malformed aspect ratio: $s, $e")
            }
        }

        /**
         * Returns une instance de [AspectRatio] spécifiée par les valeurs de [x] et [y].
         * Les valeurs de [x] et [y] seront divisées par leurs plus grand commun diviseur.
         *
         * @param x The width
         * @param y The height
         * @return an instance of [AspectRatio]
         */
        fun of(x: Int, y: Int): AspectRatio {
            var x = x
            var y = y
            val gcd = gcd(x, y)
            x /= gcd
            y /= gcd
            var arrayX = sCache[x]
            return if (arrayX == null) {
                val ratio = AspectRatio(x, y)
                arrayX = SparseArrayCompat()
                arrayX.put(y, ratio)
                sCache.put(x, arrayX)
                ratio
            } else {
                arrayX[y]!!
            }
        }

        override fun createFromParcel(parcel: Parcel): AspectRatio {
            val x = parcel.readInt()
            val y = parcel.readInt()
            return AspectRatio.of(x, y)
        }

        override fun newArray(size: Int): Array<AspectRatio?> {
            return arrayOfNulls(size)
        }
    }
}