package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_layout_sample_text)
    TextView sampleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        sampleText.setText("fooo");
    }
}
