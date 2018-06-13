package com.ajmalrasi.newsai;

import android.content.res.AssetManager;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import edu.stanford.nlp.math.ArrayMath;
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

    private static final String MODEL_FILE = "file:///android_asset/optimized_trained_model.pb";
    private static final String INPUT_NODE = "InputData/X";
    private static final String[] OUTPUT_NODE = new String[]{"FullyConnected_2/Softmax"};
    private static final String OUTPUT_NAME = "FullyConnected_2/Softmax";
    private static final long[] INPUT_SIZE = {1, 2639};
    private TensorFlowInferenceInterface tensorflow;


    public Utils(AssetManager asset) {
        this.parser = new JSONParser();
        this.assetManager = asset;
        this.tensorflow = new TensorFlowInferenceInterface(this.assetManager, MODEL_FILE);
        this.stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
    }


    public String predict(String title) {
        String label = "Undefined";

        float[] bags = new float[2639];
        try {
            float[] bag = read(title);
            bags = bag;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        tensorflow.feed(INPUT_NODE, bags, INPUT_SIZE);
        tensorflow.run(OUTPUT_NODE);
        float[] result = new float[76];
        tensorflow.fetch(OUTPUT_NAME, result);
        int argMax = ArrayMath.argmax(result);
        try {
            label = getLabel(argMax);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return label;
    }

    private float[] read(String str) throws IOException, ParseException {

        String cleaned = str.replaceAll("[^a-zA-Z]"," ")
                    .replaceAll("\\x20\\w{1,2}\\x20"," ");
        String[] temp = cleaned.toLowerCase().split("\\s");
        sentence = new ArrayList<>();
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

    private String getLabel(int index) throws IOException, ParseException {
        InputStream inputStream = assetManager.open(LABEL);
        JSONArray jsonArray = (JSONArray) parser.parse(new InputStreamReader(inputStream));
        return String.valueOf(jsonArray.get(index));
    }

    private float rnd(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
