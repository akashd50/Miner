package com.greymatter.miner.opengl.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.greymatter.miner.AppServices;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {

    public static Bitmap loadImageResource(String imagePath) {
        try {
            InputStream ins = AppServices.getAssetManager().open(imagePath);
//            final BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inScaled = false;
            return BitmapFactory.decodeStream(ins);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String loadFileResource(String filePath) {
        StringBuilder output = new StringBuilder();
        try {
            InputStream ins = AppServices.getAssetManager().open(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ins));
            String line = "";
            while((line = bufferedReader.readLine())!=null){
                output.append(line+"\n");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        Log.v("Load File Resource: ", output.toString());
        return output.toString();
    }
}
