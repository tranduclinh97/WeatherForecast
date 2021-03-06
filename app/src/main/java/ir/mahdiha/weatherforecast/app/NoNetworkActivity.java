package ir.mahdiha.weatherforecast.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import ir.mahdiha.weatherforecast.R;
import ir.mahdiha.weatherforecast.customviews.PersianButton;
import ir.mahdiha.weatherforecast.helper.HelperFunctions;

public class NoNetworkActivity extends AppCompatActivity implements View.OnClickListener
{

    private static final String KEY_EXTRA_STARTER_NAME = "KEY_EXTRA_STARTER_NAME";

    private PersianButton mRetryButton;
    private PersianButton mCheckSettingsButton;

    private String mStarterName;

    public static void start(Context context , String starterName )
    {
        Intent starter = new Intent( context , NoNetworkActivity.class);
        starter.putExtra(KEY_EXTRA_STARTER_NAME, starterName);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activityNoNetwork_ToolBar_toolbar);
        setSupportActionBar(toolbar);

        mStarterName = getIntent().getStringExtra(KEY_EXTRA_STARTER_NAME);

        findViews();
        setOnClickListeners();

    }

    private void retry()
    {
        if (HelperFunctions.isNetworkConnected(this))
        {
            if (mStarterName.equals(LaunchActivity.class.getSimpleName()))
            { LaunchActivity.start(this); }
            finish();
        } else {
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            assert vibe != null;
            vibe.vibrate(100);
            Toast.makeText(NoNetworkActivity.this , " لطفا ابتدا به اینترنت متصل شوید " , Toast.LENGTH_SHORT).show();
        }
    }

    private void findViews()
    {
        mRetryButton = (PersianButton) findViewById(R.id.activityNoNetwork_button_retry);
        mCheckSettingsButton = (PersianButton) findViewById(R.id.activityNoNetwork_button_warning);
    }

    private void setOnClickListeners()
    {
        mRetryButton.setOnClickListener(this);
        mCheckSettingsButton.setOnClickListener(this);
    }

    private void openNetworkSetting()
    {
        try
        {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
            startActivity(intent);
        }
    }

    private void openAndroidLauncherHomeScreen()
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (HelperFunctions.isNetworkConnected(this))
        {
            if (mStarterName.equals(LaunchActivity.class.getSimpleName()))
            {
                LaunchActivity.start(this);
            } else {
                finish();
            }
        } else {
            openAndroidLauncherHomeScreen();
        }
    }

    @Override
    public void onClick(View clickedView)
    {
        switch (clickedView.getId())
        {
            case R.id.activityNoNetwork_button_retry:
                retry();
                break;

            case R.id.activityNoNetwork_button_warning:
                openNetworkSetting();
                break;
        }
    }

}
