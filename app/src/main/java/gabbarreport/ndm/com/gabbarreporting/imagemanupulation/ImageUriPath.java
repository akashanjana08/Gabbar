package gabbarreport.ndm.com.gabbarreporting.imagemanupulation;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ndm-PC on 8/19/2016.
 */
public class ImageUriPath {

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static Set<String> getfile(File dir)
    {
        Set<String> fileList = new HashSet<String>();
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0)
        {
            for (int i = 0; i < listFile.length; i++)
            {
                if (listFile[i].getName().endsWith(".png")
                        || listFile[i].getName().endsWith(".jpg")
                        || listFile[i].getName().endsWith(".jpeg")
                        || listFile[i].getName().endsWith(".gif"))

                {
                    fileList.add(listFile[i].getAbsolutePath());
                }
            }

        }
        return fileList;
    }



    public static Set<String> getMp4file(File dir)
    {
        Set<String> fileList = new HashSet<String>();
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0)
        {
            for (int i = 0; i < listFile.length; i++)
            {
                if (listFile[i].getName().endsWith(".mp3")
                        || listFile[i].getName().endsWith(".mp4"))

                {
                    fileList.add(listFile[i].getAbsolutePath());
                }
            }

        }
        return fileList;
    }
}
