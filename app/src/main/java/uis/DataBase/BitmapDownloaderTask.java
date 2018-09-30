package uis.DataBase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;


//Bitmap을 가져오는 AsyncTask
public class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;

    public BitmapDownloaderTask(ImageView imageView) {
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        if (imageViewWeakReference != null) {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return downloadBitmap(strings[0]);
    }

    public Bitmap downloadBitmap(String url) {
        if(isCancelled()){
            return null;
        }
        try {

            URL URL = new URL(url);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
//            options.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeStream(URL.openStream(),null,options);

            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

