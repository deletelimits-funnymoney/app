package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Inject
    PostbankAPI postbankAPI;

    @BindView(R.id.main_layout_sample_text)
    TextView sampleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        postbankAPI.getApiInfo().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                try {
                    sampleText.setText(response.body().getString("current_user_url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @OnClick(R.id.main_layout_button)
    void onClick() {
        Toast.makeText(this, "click!!", Toast.LENGTH_SHORT).show();
    }

}
