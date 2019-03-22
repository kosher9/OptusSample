package com.github.kosher9.optus

abstract class OptusImpl(callback: Callback, previewImpl: PreviewImpl) {

    protected val mPreview: PreviewImpl = previewImpl
    protected val mCallback: Callback = callback

    /*fun getView() : View{

    }*/

    interface Callback {

        fun onCameraOpened()

        fun onCameraClosed()

        fun onPictureTaken(data: ByteArray)

    }
}