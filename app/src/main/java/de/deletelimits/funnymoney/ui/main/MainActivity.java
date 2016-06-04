package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;


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

    }

    @OnClick(R.id.main_layout_button)
    void onClick() {
        Toast.makeText(this, "click!!", Toast.LENGTH_SHORT).show();
    }

}
