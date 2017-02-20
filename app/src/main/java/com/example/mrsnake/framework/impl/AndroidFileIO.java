package com.example.mrsnake.framework.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.example.mrsnake.framework.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AndroidFileIO implements FileIO {

    Context mContext;
    AssetManager mAssetManager;
    String mExternalStoragePath;
    File mExternalFilesDir;

    public AndroidFileIO(Context context) {
        this.mContext = context;
        this.mAssetManager = context.getAssets();
        this.mExternalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        this.mExternalFilesDir = context.getExternalFilesDir(null);
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException {
        return mAssetManager.open(fileName);
    }

    @Override
    public InputStream readFile(String fileName) throws IOException {
        return new FileInputStream(new File(mExternalFilesDir, fileName));
    }

    @Override
    public OutputStream writeFile(String fileName) throws IOException {
        return new FileOutputStream(new File(mExternalFilesDir, fileName));
    }

    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }
}
