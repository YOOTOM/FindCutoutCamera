package com.yootom.findcutoutcarmeraapp.Utils

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.DisplayCutout
import android.view.WindowInsets
import java.lang.reflect.Method


class DisplayCutoutHelper {

    private val CUTOUT_TYPE_NONE = "None"
    private val CUTOUT_TYPE_LEFT_CONER = "Left Coner Display Cutout"
    private val CUTOUT_TYPE_RIGHT_CONER = "Right Coner Display Cutout"
    private val CUTOUT_TYPE_DOUBLE = "Double Display Cutout"
    private val CUTOUT_TYPE_CENTER = "Center Display Cutout"

    lateinit var disCutoutBound: MutableList<Rect>
    var disCutoutLeft: Int = 0
    var disCutoutTop: Int = 0
    var disCutoutRight: Int = 0
    var disCutoutBottom: Int = 0
    lateinit var disCutoutType: String
    var disCutoutHeight: Int = 0
    var activity: Activity

    lateinit var disCutoutRect: Rect


    constructor(activity: Activity) {
        this.activity = activity
    }

    //check Cutout
    fun checkCoutout(displayCutout: DisplayCutout?): Boolean {
        var result = false
        if (displayCutout != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                result = true

                //Bounding
                disCutoutBound = displayCutout.boundingRects
                disCutoutRect = (disCutoutBound as MutableList<Rect>)[0]

                //Bounding_Slice
                disCutoutLeft = disCutoutRect.left
                disCutoutRight = disCutoutRect.right
                disCutoutTop = disCutoutRect.top
                disCutoutBottom = disCutoutRect.bottom

                //Type
                disCutoutType = cutoutType(
                    displayCutout.safeInsetTop,
                    displayCutout.safeInsetBottom,
                    disCutoutLeft,
                    disCutoutRight
                )

                //Height
                disCutoutHeight = displayCutout.safeInsetTop
            } else {
                result = false
            }
        }
        return result
    }

    private fun cutoutType(top: Int, bottom: Int, b_left: Int, b_right: Int): String {
        var type = CUTOUT_TYPE_NONE
        val size = Point();
        val display = activity.getWindowManager().getDefaultDisplay()
        display.getSize(size);

        //더블
        if (top > 0 && bottom > 0) {
            type = CUTOUT_TYPE_DOUBLE
            //코너 왼/오/일반
        } else if (top > 0 && bottom == 0) {
            if (Math.abs(Integer.parseInt(b_left.toString())) == Math.abs(size.x)) {
                type = CUTOUT_TYPE_LEFT_CONER
            } else if (Math.abs(Integer.parseInt(b_right.toString())) == Math.abs(size.x)) {
                type = CUTOUT_TYPE_RIGHT_CONER
            } else {
                //센터
                type = CUTOUT_TYPE_CENTER
            }
        } else {
            //없음,널
            type = CUTOUT_TYPE_NONE
        }
        return type
    }
}
