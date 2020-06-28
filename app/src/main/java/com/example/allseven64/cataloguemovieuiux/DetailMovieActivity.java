package com.example.allseven64.cataloguemovieuiux;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.allseven64.cataloguemovieuiux.db.MovieHelper;
import com.example.allseven64.cataloguemovieuiux.model.MovieModel;
import com.example.allseven64.cataloguemovieuiux.fragment.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.CONTENT_URI;
import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.MovieColums.OVERVIEW;
import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.MovieColums.POPULARITY;
import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.MovieColums.POSTER;
import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.MovieColums.RELEASE;
import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.MovieColums.TITLE;
import static com.example.allseven64.cataloguemovieuiux.db.DatabaseContract.MovieColums.VOTE;

public class DetailMovieActivity extends AppCompatActivity {
    public static String EXTRA_POSTER = "EXTRA_POSTER";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_OVERVIEW = "EXTRA_OVERVIEW";
    public static String EXTRA_RELEASE = "EXTRA_RELEASE";
    public static String EXTRA_POPULARITY = "EXTRA_POPULARITY";
    public static String EXTRA_VOTE = "EXTRA_VOTE";

    public static String EXTRA_MOVIE = "extra_movies";
    public static String EXTRA_POSITION = "extra_position";

    private TextView tvTitle, tvOverview, tvRelease, tvPopularity, tvAddRemove, tvVote;
    private ImageView imgPoster;
    private ToggleButton toggleButton;
    private MovieModel favMovieModel;
    private MovieHelper movieHelper;

    SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvRelease = findViewById(R.id.tv_release);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvVote = findViewById(R.id.tv_vote);
        tvAddRemove = findViewById(R.id.tv_add_remove);
        toggleButton = findViewById(R.id.myToggleButton);


        final String poster = getIntent().getStringExtra(EXTRA_POSTER);
        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        final String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        final String release = getIntent().getStringExtra(EXTRA_RELEASE);
        final String popularity = getIntent().getStringExtra(EXTRA_POPULARITY);
        final String voteAverage = getIntent().getStringExtra(EXTRA_VOTE);

        sharedPreference = new SharedPreference(this);

        if (sharedPreference.loadDarkModeState()==true) {
            setTheme(R.style.DarkTheme);
        }
        else setTheme(R.style.AppTheme);

        String release_date = "";
        getSupportActionBar().setTitle(title);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        favMovieModel = getIntent().getParcelableExtra(EXTRA_MOVIE);

        if (favMovieModel != null) {
            toggleButton.setChecked(true);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_yellow));
            tvAddRemove.setText(R.string.remove_favorite);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(favMovieModel.getReleaseDate());
                SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                release_date = newDateFormat.format(date);
                tvRelease.setText(release_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvTitle.setText(favMovieModel.getTitle());
            tvOverview.setText(favMovieModel.getOverview());
            tvVote.setText(favMovieModel.getVote_average());
            tvPopularity.setText(getResources().getString(R.string.popularity) + ": " + favMovieModel.getPopularity());
            toolbar.setTitle(favMovieModel.getTitle());

            Glide.with(this).load(favMovieModel.getPosterPath()).into(imgPoster);

        } else {
            toggleButton.setChecked(false);
            toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_grey));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(release);
                SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                release_date = newDateFormat.format(date);
                tvRelease.setText(release_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tvTitle.setText(title);
            tvOverview.setText(overview);
            tvVote.setText(voteAverage);
            tvPopularity.setText(getResources().getString(R.string.popularity) + ": " + popularity);

            Glide.with(this).load(poster).into(imgPoster);

        }

        Uri uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) favMovieModel = new MovieModel(cursor);
                cursor.close();
            }
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean on = toggleButton.isChecked();

                if (on) {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_yellow));
                    //Gunakan contentValues untuk menampung data
                    ContentValues values = new ContentValues();
                    values.put(POSTER, poster);
                    values.put(TITLE, title);
                    values.put(OVERVIEW, overview);
                    values.put(RELEASE, release);
                    values.put(POPULARITY, popularity);
                    values.put(VOTE, voteAverage);

                    String message = "Kamu baru saja menambahkan " + title + " ke favorit";
                    Context context;
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(DetailMovieActivity.this)
                            .setSmallIcon(R.drawable.ic_home)
                            .setContentTitle("New Notification")
                            .setContentText(message).setAutoCancel(true);

                    Intent notificationIntent = new Intent(DetailMovieActivity.this, HomeFragment.class);
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    notificationIntent.putExtra("message", message);
                    PendingIntent pendingIntent = PendingIntent.getActivity(DetailMovieActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingIntent);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0,builder.build());

                    getContentResolver().insert(CONTENT_URI, values);
                    Toast.makeText(getApplicationContext(), R.string.toast_add, Toast.LENGTH_SHORT).show();
                    Log.d("tambah", "tb is checked");
                    finish();
                } else {
                    toggleButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_grey));
                    getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + favMovieModel.getId()), null, null);
                    Toast.makeText(getApplicationContext(), R.string.toast_remove, Toast.LENGTH_SHORT).show();
                    Log.d("hapus", "tb is unchacked");
                    finish();
                }

            }
        });
    }


}
