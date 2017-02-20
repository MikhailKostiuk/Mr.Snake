package com.example.mrsnake;


import com.example.mrsnake.framework.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Settings {

    public static boolean sSoundEnabled = true;
    public static int[] sHighscores= new int[] { 0, 0, 0, 0, 0 };

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(files.readFile(".mrsnake")));
            sSoundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                sHighscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException | NumberFormatException e) {
            // It's ok, we have defaults
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile(".mrsnake")));
            out.write(Boolean.toString(sSoundEnabled));
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(sHighscores[i]));
            }
        } catch (IOException e) {

        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {

            }
        }
    }
    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (sHighscores[i] < score) {
                for (int j = 4; j > i; j--) {
                    sHighscores[j] = sHighscores[j - 1];
                }
                sHighscores[i] = score;
                break;
            }
        }
    }
}
