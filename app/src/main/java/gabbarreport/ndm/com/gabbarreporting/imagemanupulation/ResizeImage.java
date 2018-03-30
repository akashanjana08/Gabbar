package gabbarreport.ndm.com.gabbarreporting.imagemanupulation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ndm-PC on 4/13/2016.
 */
public class ResizeImage
{

    public Bitmap getBitmap(Uri uri)
    {


        try
        {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(uri.getPath());

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(uri.getPath());
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth/1000, inHeight/1000);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, 1000, 1000);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);

            // save image
            try
            {
                FileOutputStream out = new FileOutputStream(uri.getPath());
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                return  resizedBitmap;
            }
            catch (Exception e)
            {
                Log.e("Image", e.getMessage(), e);
            }
        }
        catch (IOException e)
        {
            Log.e("Image", e.getMessage(), e);
        }
        return  null;
    }

}
