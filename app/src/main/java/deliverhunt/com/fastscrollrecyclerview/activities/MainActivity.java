package deliverhunt.com.fastscrollrecyclerview.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import deliverhunt.com.fastscrollrecyclerview.R;

public class MainActivity extends AppCompatActivity
{
    private final String TAG = MainActivity.class.getSimpleName();
    private EditText editText;
    private Context context;
    private final int RESULT_CODE = 123;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        editText = (EditText) findViewById(R.id.search_city);
        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        editText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
                startActivityForResult(intent, RESULT_CODE);
            }
        });
    }

    private void setSelectedCity(Intent data)
    {
        String city = data.getStringExtra("Keys");
        editText.setText(city);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case RESULT_CODE:
                    setSelectedCity(data);
                    break;
            }
        }
    }
}
