package com.github.danielfelgar.drawreceiptlib;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class DrawMultilineText extends DrawText {

    private final int width;

    public DrawMultilineText(String text, int width) {
        super(text);
        this.width = width;
    }

    @Override
    public void drawOnCanvas(Canvas canvas, float x, float y) {
        drawMultiLineText(text, x, y, paint, canvas);
    }

    @Override
    public int getHeight() {
        return computeLineAmount() * getTextHeight();
    }

    private void drawMultiLineText(String str, float x, float y, Paint paint, Canvas canvas) {
        String[] lines = str.split("\n");
        float xx = getX(canvas, x);
        float yy = getY(y);
        float height = 0;

        for (String line : lines) {

            final List<String> subLines = new ArrayList<>();
            makeSecureLines(line, paint, width, subLines);

            for (String subTxt : subLines) {
                canvas.drawText(subTxt, xx, yy + height, paint);
                height += getTextHeight();
            }
        }
    }

    private int makeSecureLines(String text, Paint mPaint, float secureLineWidth, List<String> lines) {

        if (TextUtils.isEmpty(text)) {
            lines.add("");
            return 1;
        }
        int measuredNum = mPaint.breakText(text, true, secureLineWidth, null);
        lines.add(text.substring(0, measuredNum));
        String leftStr = text.substring(measuredNum);

        if(leftStr.length() > 0) makeSecureLines(leftStr, mPaint, secureLineWidth, lines);
        return lines.size();
    }

    private int computeLineAmount() {
        String[] lines = text.split("\n");
        int inc = 0;
        ArrayList<String> arr = new ArrayList<>();
        for (String line : lines) {
            arr.clear();
            inc += makeSecureLines(line, paint, width, arr);
        }
        return inc;
    }
}
