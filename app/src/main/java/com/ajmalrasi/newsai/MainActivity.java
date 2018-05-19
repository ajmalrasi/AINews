package com.ajmalrasi.newsai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import org.json.simple.parser.ParseException;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
import java.io.IOException;


import edu.stanford.nlp.math.ArrayMath;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "APP";

    private TextView mTextMessage;
    private static final String MODEL_FILE = "file:///android_asset/optimized_trained_model.pb";
    private static final String INPUT_NODE = "InputData/X";
    private static final String[] OUTPUT_NODE = new String[] {"FullyConnected_3/Softmax"};
    private static final String OUTPUT_NAME = "FullyConnected_3/Softmax";
    private static final long[] INPUT_SIZE = {1,1421};
    private TensorFlowInferenceInterface inferenceInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inferenceInterface = new TensorFlowInferenceInterface(getAssets(),MODEL_FILE);

        float[] bags = new float[1421];
        Utils utils = new Utils(getAssets());

        try {
            float[] bag = utils.read("Jennifer Lawrence: I was treated 'in a way that now we would call abusive");
            bags = bag;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        inferenceInterface.feed(INPUT_NODE,bags,INPUT_SIZE);
        inferenceInterface.run(OUTPUT_NODE);

        float[] result = new float[51];
        inferenceInterface.fetch(OUTPUT_NAME,result);
        int argMax = ArrayMath.argmax(result);

        try {
            Log.e(TAG, "onCreate: "+argMax+" : "+ utils.getLabel(argMax));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


}
