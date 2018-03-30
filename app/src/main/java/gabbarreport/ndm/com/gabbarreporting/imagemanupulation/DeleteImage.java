package gabbarreport.ndm.com.gabbarreporting.imagemanupulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Ndm-PC on 8/23/2016.
 */
public class DeleteImage
{
   public static void delete(File f) throws IOException {
       if (f.isFile())
       {
           f.delete();
           for (File c : f.listFiles())
           {
               delete(c);
           }
       }
       else if (f.getAbsolutePath().endsWith("FIR"))
       {
           if (!f.delete())
           {
               new FileNotFoundException("Failed to delete file: " + f);
           }
       }
   }
}
