package com.ajmalrasi.newsai;

import android.content.res.AssetManager;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

/**
 * Created by Rasi on 18-05-2018.
 */

public class Utils {

    private static final String WORDS = "words.json";
    private static final String LABEL = "topics.json";
    private JSONParser parser;
    private ArrayList<String> sentence;
    private AssetManager assetManager;
    private SnowballStemmer stemmer;


    public Utils(AssetManager asset) {
        this.parser = new JSONParser();
        this.sentence = new ArrayList<>();
        this.assetManager = asset;
    }

    public float[] read(String str) throws IOException, ParseException {

        String cleaned = str.replaceAll("[^a-zA-Z]"," ")
                    .replaceAll("\\x20\\w{1,2}\\x20"," ");
        String[] temp = cleaned.toLowerCase().split("\\s");
        stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
        for (String cha:temp ) {
            sentence.add(String.valueOf(stemmer.stem(cha)));
        }

        InputStream inputStream = assetManager.open(WORDS);
            JSONArray jsonArray = (JSONArray) parser.parse(new InputStreamReader(inputStream));
            float[] bag = new float[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                if (sentence.contains(jsonArray.get(i))) bag[i] = 1;
                else{
                    bag[i] = 0;
                }
            }
        return bag;
    }

    public String getLabel(int index) throws IOException, ParseException {
        InputStream inputStream = assetManager.open(LABEL);
        JSONArray jsonArray = (JSONArray) parser.parse(new InputStreamReader(inputStream));
        return String.valueOf(jsonArray.get(index));
    }

    public float rnd(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
