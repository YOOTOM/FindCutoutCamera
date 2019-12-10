package com.yootom.findcutoutcarmeraapp.Utils

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.DisplayCutout
import kotlin.math.abs

class DisplayCutoutSimpleHelperHelper(private var activity: Activity) {

    private val TAG: String = DisplayCutoutSimpleHelperHelper::class.java.simpleName
    private val CUTOUT_TYPE_NONE = "None"
    private val CUTOUT_TYPE_LEFT_CORNER = "Left Corner Display Cutout"
    private val CUTOUT_TYPE_RIGHT_CORNER = "Right Corner Display Cutout"
    private val CUTOUT_TYPE_DOUBLE = "Double Display Cutout"
    private val CUTOUT_TYPE_CENTER = "Center Display Cutout"

    companion object {
        //get DisplayCutout
        @JvmStatic
        var disCutoutArray: Array<String?> = arrayOfNulls(7)

        //get DisplayCutout Status
        @JvmStatic
        var checkCutoutBoolean: Boolean = false
    }

    //initialization DisplayCutout
    fun initDisCutoutSetting(): Unit {
        Log.d(TAG, "Start, initDisCutoutSetting()")
        checkCutoutBoolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (checkCoutout(activity.window.decorView.rootWindowInsets.displayCutout)) {
                for (parsing: String? in disCutoutArray) {
                    Log.d(TAG, "DisplayCutout : $parsing")
                }
                true
            } else {
                Log.d(TAG, "DisplayCutout : is not")
                false
            }
        } else {
            Log.d(TAG, "DisplayCutout : This device does not support")
            false
        }
    }

    //check Cutout
    private fun checkCoutout(displayCutout: DisplayCutout?): Boolean {
        return if (displayCutout != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                lateinit var disCutoutType: String
                lateinit var disCutoutRect: Rect

                //Rect
                val disCutoutBound: MutableList<Rect> = displayCutout.boundingRects
                disCutoutRect = disCutoutBound[0]

                //Bounding_Slice
                val disCutoutLeft: Int = disCutoutRect.left
                val disCutoutRight: Int = disCutoutRect.right
                val disCutoutTop: Int = disCutoutRect.top
                val disCutoutBottom: Int = disCutoutRect.bottom

                //Type
                disCutoutType = cutoutType(
                    displayCutout.safeInsetTop,
                    displayCutout.safeInsetBottom,
                    disCutoutLeft,
                    disCutoutRight
                )

                //Height
                val disCutoutHeight: Int = displayCutout.safeInsetTop

                //Array Setting
                disCutoutArray = getDisCutout(
                    disCutoutBound,
                    disCutoutLeft,
                    disCutoutTop,
                    disCutoutRight,
                    disCutoutBottom,
                    disCutoutType,
                    disCutoutHeight
                )
                Log.d(TAG, "Created, DisplayCutout")
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    //get DisplayCutout Type
    private fun cutoutType(top: Int, bottom: Int, b_left: Int, b_right: Int): String {
        var type: String = CUTOUT_TYPE_NONE
        val size = Point();
        val display: Display = activity.windowManager.defaultDisplay
        display.getSize(size);

        //Double
        if (top > 0 && bottom > 0) {
            type = CUTOUT_TYPE_DOUBLE
            //Corner left/right/center
        } else if (top > 0 && bottom == 0) {
            type = when {
                abs(Integer.parseInt(b_left.toString())) == abs(size.x) -> {
                    CUTOUT_TYPE_LEFT_CORNER
                }
                abs(Integer.parseInt(b_right.toString())) == abs(size.x) -> {
                    CUTOUT_TYPE_RIGHT_CORNER
                }
                else -> {
                    CUTOUT_TYPE_CENTER
                }
            }
        } else {
            //null, none
            type = CUTOUT_TYPE_NONE
        }
        return type
    }

    //get DisplayCutout setting value (array)
    private fun getDisCutout(
        rect: MutableList<Rect>,
        rectLeft: Int,
        rectTop: Int,
        rectRight: Int,
        rectBottom: Int,
        dcType: String,
        dcHeight: Int
    ): Array<String?> {
        val result: Array<String?> = arrayOfNulls(7)
        result[0] = rect.toString()
        result[1] = rectLeft.toString()
        result[2] = rectTop.toString()
        result[3] = rectRight.toString()
        result[4] = rectBottom.toString()
        result[5] = dcType
        result[6] = dcHeight.toString()
        return result
    }
}
