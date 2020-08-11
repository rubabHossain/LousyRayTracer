package com.raytracer.lib.canvas;

import com.raytracer.lib.primitives.Color;

import java.util.List;

public class PpmWriter {

    private static final int TOP_LIMIT = 255;

    public static String toFmtString(Canvas canvas) {

        StatefulStringBuilder stsb = new StatefulStringBuilder();
        stsb.appendUnit("P3").endLine();
        stsb.appendUnit(canvas.getWidth() + "").appendUnit(canvas.getHeight() + "").endLine();
        stsb.appendUnit(TOP_LIMIT + "").endLine();

        for(List<Color> row: canvas.getPixelGrid()) {
            for(Color c : row) {
                String r = colorValueToScaledString(c.getRed());
                String g = colorValueToScaledString(c.getGreen());
                String b = colorValueToScaledString(c.getBlue());
                stsb.appendUnit(r).appendUnit(g).appendUnit(b);
            }
            stsb.endLine();
        }

        return stsb.toString();
    }


    private static String colorValueToScaledString(Double inp) {
        double roundedInp = Math.round(inp * PpmWriter.TOP_LIMIT);
        roundedInp = Math.max(roundedInp, 0);
        roundedInp = Math.min(roundedInp, PpmWriter.TOP_LIMIT);
        return ((int) roundedInp) + "";
    }


    private static String getPpmHeader(Canvas canvas) {
        return "P3" + System.lineSeparator() +
                canvas.getWidth() + " " + canvas.getHeight() + System.lineSeparator() +
                PpmWriter.TOP_LIMIT;
    }

}

class StatefulStringBuilder {
    private final int LINE_LIMIT = 70;
    StringBuilder sb;
    int curCharsInLine;

    public StatefulStringBuilder() {
        this.sb = new StringBuilder();
        this.curCharsInLine = 0;
    }

    @Override
    public String toString() {
        return this.sb.append(System.lineSeparator()).toString();
    }


    public StatefulStringBuilder appendUnit(final String unit) {

        // figure out if we can fit incoming unit on current line. If not, make a new line
        if(curCharsInLine + unit.length() + 1> LINE_LIMIT) { this.endLine(); }


        // if not currently at the beginning of a new line, append a space to sep from prev unit.
        if(curCharsInLine != 0) {
            this.sb.append(" ");
            this.curCharsInLine += 1;
        }

        sb.append(unit);
        this.curCharsInLine += unit.length();

        return this;
    }

    public void endLine() {
        this.sb.append(System.lineSeparator());
        this.curCharsInLine = 0;
    }

}
