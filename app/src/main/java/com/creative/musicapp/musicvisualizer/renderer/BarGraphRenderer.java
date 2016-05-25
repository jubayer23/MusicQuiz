/**
 * Copyright 2011, Felix Palmer
 * <p/>
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.creative.musicapp.musicvisualizer.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.creative.musicapp.appdata.AppConstant;
import com.creative.musicapp.musicvisualizer.visualizer.AudioData;
import com.creative.musicapp.musicvisualizer.visualizer.FFTData;


public class BarGraphRenderer extends Renderer {
    private int mDivisions;
    private Paint mPaint;
    private boolean mTop;

    /**
     * Renders the FFT data as a series of lines, in histogram form
     * @param divisions - must be a power of 2. Controls how many lines to draw
     * @param paint - Paint to draw lines with
     * @param top - whether to draw the lines at the top of the canvas, or the bottom
     */
    public BarGraphRenderer(int divisions,
                            Paint paint,
                            boolean top) {
        super();
        mDivisions = divisions;
        mPaint = paint;
        mTop = top;
    }

    @Override
    public void onRender(Canvas canvas, AudioData data, Rect rect) {
        // Do nothing, we only display FFT data
    }

    @Override
    public void onRender(Canvas canvas, FFTData data, Rect rect) {
        for (int i = 0; i < data.bytes.length / mDivisions; i++) {
            mFFTPoints[i * 4] = i * 4 * mDivisions;
            mFFTPoints[i * 4 + 2] = i * 4 * mDivisions;
            byte rfk = data.bytes[mDivisions * i];
            byte ifk = data.bytes[mDivisions * i + 1];
            float magnitude = (rfk * rfk + ifk * ifk);


            if (mTop) {

                int dbValue = (int) (AppConstant.musicVisualizerHeightTop * Math.log10(magnitude));
                mFFTPoints[i * 4 + 1] = 0;
                mFFTPoints[i * 4 + 3] = (dbValue * 2 - 10);
            } else {
                int dbValue = (int) (AppConstant.musicVisualizerHeightBottom * Math.log10(magnitude));
                mFFTPoints[i * 4 + 1] = rect.height();
                mFFTPoints[i * 4 + 3] = rect.height() - (dbValue * 2 - 10);
            }
        }

        canvas.drawLines(mFFTPoints, mPaint);
    }
}
