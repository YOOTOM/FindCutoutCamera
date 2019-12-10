package com.yootom.findcutoutcarmeraapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yootom.findcutoutcarmeraapp.Utils.DisplayCutoutHelper
//import com.yootom.findcutoutcarmeraapp.Utils.DisplayCutoutSimpleHelperHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.canonicalName
    private lateinit var disCutout: DisplayCutoutHelper


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disCutout = DisplayCutoutHelper(this)

        btn_main.setOnClickListener {
            //Cutout 기능은 안드로이드 9.0(sdk 28)인 Pie 이상부터 사용가능
            //그러므로 체크는 필수
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (disCutout.checkCoutout(window.decorView.rootWindowInsets.displayCutout)) {
                    if (disCutout.disCutoutBound != null) {
                        Log.d(
                            TAG, "DisplatCoutout: info, " + "\n" +
                                    "Bound: ${disCutout.disCutoutBound} " + "\n" +
                                    "Left: ${disCutout.disCutoutLeft} " + "\n" +
                                    "Right: ${disCutout.disCutoutRight} " + "\n" +
                                    "Top: ${disCutout.disCutoutTop}" + "\n" +
                                    "Bottom: ${disCutout.disCutoutBottom}" + "\n" +
                                    "Type: ${disCutout.disCutoutType}" + "\n" +
                                    "Height: ${disCutout.disCutoutHeight}" + "\n"
                        )
                        txt_main.text = "DisplatCoutout: info " + "\n\n" +
                                "Bound: ${disCutout.disCutoutBound} " + "\n" +
                                "Left: ${disCutout.disCutoutLeft} " + "\n" +
                                "Right: ${disCutout.disCutoutRight} " + "\n" +
                                "Top: ${disCutout.disCutoutTop}" + "\n" +
                                "Bottom: ${disCutout.disCutoutBottom}" + "\n\n" +
                                "Type: ${disCutout.disCutoutType}" + "\n\n" +
                                "Height: ${disCutout.disCutoutHeight}" + "\n"

                        val rect_view = DrawingView(
                            this,
                            disCutout.disCutoutLeft,
                            disCutout.disCutoutTop,
                            disCutout.disCutoutRight,
                            disCutout.disCutoutBottom
                        )
                        linearLayout_main.addView(rect_view)
                    }
                } else {
                    Log.d(TAG, "DisplayCutout: is not")
                    txt_main.text = "DisplayCutout: is not"
                }
            } else {
                Log.d(TAG, "This device does not support")
                txt_main.text = "This device does not support"
            }
        }
    }

    @SuppressLint("ViewConstructor")
    class DrawingView : View {
        internal var left: Int = 0
        internal var top: Int = 0
        internal var right: Int = 0
        internal var botton: Int = 0

        constructor(
            context: Context?,
            left: Int,
            top: Int,
            right: Int,
            botton: Int
        ) : super(context) {
            this.left = left
            this.botton = botton
            this.right = right
            this.botton = botton
            this.top = top
        }

        @SuppressLint("DrawAllocation")
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val paint = Paint()
            paint.color = Color.RED
            paint.isAntiAlias = true

            //사각형 그리기
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), botton.toFloat(), paint)
        }
    }

    /**
     * DisplayCutoutSimpleHelper Type
     * 좀더 심플하게 불러와 작업 할수 있다.
     */

    //화면에 뷰가 추가될때 호출
    //getRootWindowInset를 사용하기 위해 onAttachedToWindow()를 오버라이드
    /* override fun onAttachedToWindow() {
         super.onAttachedToWindow()
         DisplayCutoutSimpleHelperHelper(this).initDisCutoutSetting()
     }

    //단말기의 컷 아웃 정보를 배열로 가져온다.
    /*
     *  배열 구조
     *  String[0] : rect: MutableList<Rect>
     *  String[1] : rectLeft: Int
     *  String[2] : rectTop: Int
     *  String[3] : rectRight: Int
     *  String[4] : rectBottom: Int
     *  String[5] : dcType: String
     *  String[6] : cHeight: Int
     */
     fun getDisCutoutArray(): Array<String?>? {
         Log.d(TAG, "Start, getDisCutoutArray")
         return DisplayCutoutSimpleHelperHelper.disCutoutArray
     }

    //단말기의 커 아웃 상태를 체크한다.
    /*
     *  true : Cutout(North) 존재 O
     *  false : Cutout(North) 존재 X
     */
     fun checkDisCutoutBoolean(): Boolean {
         Log.d(TAG, "Start, checkDisCutout")
         return DisplayCutoutSimpleHelperHelper.checkCutoutBoolean
     }*/

}
