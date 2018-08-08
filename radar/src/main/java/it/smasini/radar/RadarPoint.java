package it.smasini.radar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Simone Masini on 12/10/2016.
 */
public class RadarPoint {

    public float x;
    public float y;
    public int radius;
    public String identifier;
    private String imageUrl;
    private Bitmap bitmap;
    private boolean bitmapLoaded = false;
    private boolean bitmapLoadedError = false;

    public RadarPoint(String identifier, float x, float y, int radius){
        this.identifier = identifier;
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public RadarPoint(String identifier, float x, float y){
        this.identifier = identifier;
        this.x = x;
        this.y = y;
    }

    public RadarPoint(String identifier, float x, float y, int radius, String imageUrl) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.identifier = identifier;
        this.imageUrl = imageUrl;
    }

    public RadarPoint(String identifier, float x, float y, String imageUrl) {
        this.x = x;
        this.y = y;
        this.identifier = identifier;
        this.imageUrl = imageUrl;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitmapLoaded = true;
    }

    public Bitmap getBitmap() {

        Bitmap circularBitmap = getCroppedBitmap(bitmap);

    //            getCircularBitmap(bitmap);

        // Add a border around circular bitmap
    //    circularBitmap = addBorderToCircularBitmap(bitmap, 15, Color.WHITE);

        // Add a shadow around circular bitmap
    //    circularBitmap = addShadowToCircularBitmap(bitmap, 4, Color.LTGRAY);


        return circularBitmap;
    }

    public boolean isBitmapLoaded() {
        return bitmapLoaded;
    }

    public boolean isBitmapLoadedError() {
        return bitmapLoadedError;
    }

    public void setBitmapLoadedError(boolean bitmapLoadedError) {
        this.bitmapLoadedError = bitmapLoadedError;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}
