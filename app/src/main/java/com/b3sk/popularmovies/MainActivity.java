package com.b3sk.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.b3sk.popularmovies.Models.MovieInfo;

public class MainActivity extends AppCompatActivity implements MovieFragment.MovieCallback {

    private static final String MOVIE_FRAGMENT_TAG = "MFTAG";
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            twoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new InfoActivityFragment(), MOVIE_FRAGMENT_TAG)
                        .commit();
            }


        } else twoPane = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(MovieInfo movie) {

        // In two-pane mode, show the detail view in this activity by
        // adding or replacing the detail fragment using a
        // fragment transaction.
        Bundle args = new Bundle();
        args.putParcelable(MovieFragment.PAR_KEY, movie);
        if (twoPane) {
            InfoActivityFragment fragment = new InfoActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, MOVIE_FRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, InfoActivity.class);
            intent.putExtras(args);
            startActivity(intent);
        }

    }
}
