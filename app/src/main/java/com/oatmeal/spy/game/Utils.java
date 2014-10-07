package com.oatmeal.spy.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Utils {

  public int debug_id;

  public Utils(int di){
     this.debug_id = di;
  }

  static public void saveBitMap(String fn, Bitmap cs){
        try {
          OutputStream os = new FileOutputStream(fn);
          cs.compress(Bitmap.CompressFormat.PNG, 100, os);
        } catch(IOException e) {
          Log.e("combineImages", "problem combining images", e);
        }
  }

  static private Bitmap combineImages(Bitmap c, Bitmap s) {
        int width = Math.max(c.getWidth(), s.getWidth());
        int height = s.getHeight() + c.getWidth() + 10;
        Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, (float) c.getHeight() + 5, null);
        return cs;
    }
}
