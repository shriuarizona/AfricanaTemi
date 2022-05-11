package com.example.temitour;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.temitour.paintings.Artist;
import com.example.temitour.paintings.Painting;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.constants.Utils;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class represents the Culture Walk activity
 *
 * @author Gavin Vogt
 */
public class TourActivity extends AppCompatActivity implements OnRobotReadyListener {

    /** This array of paintings to tour */
    private Painting[] paintings = null;
    /** ImageView to hold the painting image */
    private ImageView paintingImage;
    /** Text holding the artist name */
    private TextView artistTextView;
    /** Text holding the year the painting was created */
    private TextView yearTextView;
    /** Text holding the painting medium */
    private TextView mediumTextView;
    /** Text holding the painting measurements */
    private TextView measurementsTextView;
    /** Text holding a description of the painting */
    private TextView descriptionTextView;
    /** Button to continue the tour */
    private Button continueButton;
    /** Button to select a painting to go to */
    private Button selectButton;
    /** Image holding QR code associated with the current painting */
    private ImageView qrCodeImage;
    /** Image button to toggle volume on/off */
    private ImageButton volumeButton;
    /** Whether or not the volume is on */
    private boolean volumeIsOn = true;
    /** Keep track of current location in case Temi is already at the location */
    private String currentLocation = null;

    /** Random int generator */
    private Random rand = new Random();
    /** Low volume to avoid disturbing people */
    private static final int VOLUME_LEVEL = 2;
    /** Location of home base */
    private static final String HOME_BASE_LOCATION = "home base";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.tour_title);
        setContentView(R.layout.activity_tour_crossfade);

        // Find views
        paintingImage = findViewById(R.id.painting_image);
        artistTextView = findViewById(R.id.artist_text);
        yearTextView = findViewById(R.id.year_text);
        mediumTextView = findViewById(R.id.medium_text);
        measurementsTextView = findViewById(R.id.measurements_text);
        descriptionTextView = findViewById(R.id.description_text);
        qrCodeImage = findViewById(R.id.qr_code_image);
        continueButton = findViewById(R.id.continue_button);
        selectButton = findViewById(R.id.select_button);
        selectButton.setOnClickListener((v) -> {
            userSelectPainting();
        });
        volumeButton = findViewById(R.id.volume_image_button);
        volumeButton.setOnClickListener((v) -> {
            volumeIsOn = !volumeIsOn;
            applyVolumeSelection();
        });
        applyVolumeSelection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Robot.getInstance().addOnRobotReadyListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tour, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_finish_tour) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the volume button with image matching whether volume is on or off
     */
    private void applyVolumeSelection() {
        // Update the volume button image
        int imageId;
        if (volumeIsOn) {
            imageId = R.drawable.ic_baseline_volume_on_80;
        } else {
            imageId = R.drawable.ic_baseline_volume_off_80;
            Robot.getInstance().cancelAllTtsRequests();
        }

        Drawable volumeIcon = ResourcesCompat.getDrawable(getResources(), imageId, null);
        volumeButton.setImageDrawable(volumeIcon);
    }

    /**
     * Fades between the start layout and the actual tour layout.
     * Then begins the tour.
     */
    private void fadeBetweenLayoutsAndBeginTour() {
        // Yoinked from https://stackoverflow.com/a/11712892
        final View tourStartLayout = findViewById(R.id.tour_start_layout);
        final View tourLayout = findViewById(R.id.tour_layout);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tourStartLayout.setVisibility(View.GONE);
                beginTour();
            }
        });

        // Animate the crossfade
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                tourStartLayout.startAnimation(fadeOut);
                tourLayout.startAnimation(fadeIn);
            }
        }, 1000);
    }

    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            // TODO: on the device the app is installed on, turn on the SETTINGS permission
            Robot.getInstance().setVolume(VOLUME_LEVEL);

            // Try to load the paintings and artists
            try {
                this.loadData();
            } catch (Exception e) {
                // Failed to load data
                Log.e("abcdefg", "Error loading paintings/artists", e);
                finish();
                Toast toast = Toast.makeText(getApplicationContext(), "Failed to load painting and/or artist data",
                        Toast.LENGTH_LONG);
                toast.show();
            }

            // Update display of painting information just in case
            if (paintings.length > 0) {
                updatePaintingInfo(paintings[0]);
            }

            // Fade from the start screen to the actual tour layout
            fadeBetweenLayoutsAndBeginTour();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().removeOnRobotReadyListener(this);
        Robot.getInstance().cancelAllTtsRequests();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Robot.getInstance().cancelAllTtsRequests();
    }

    /**
     * Begins the Temi tour
     */
    private void beginTour() {
        // Begin the tour
        guideToPainting(0);
    }

    /**
     * Guides the user to the painting
     * @param i is the index of the painting in the array of Paintings
     */
    private void guideToPainting(int i) {
        if (!(i < paintings.length)) {
            return;
        }

        // Update the painting shown
        Painting painting = paintings[i];
        updatePaintingInfo(painting);

        // TODO: (only if QR codes are created for each painting) need actual resources to put in 'qrCode' field of Paintings
        // Uncomment the below qrCode lines once `qrCode` in each paintings.json Painting
        // maps to a valid QR code

        // Update the QR code for Hologram of curator talking about artist/painting
//        Drawable qrCode = ResourcesCompat.getDrawable(getResources(), painting.getQrCodeId(), null);
//        qrCodeImage.setImageDrawable(qrCode);

        // Update the Continue button
        if (i + 1 < paintings.length) {
            // Still other paintings left to go
            continueButton.setText(R.string.continue_tour);
            continueButton.setBackgroundColor(getResources().getColor(R.color.bright_blue));
            continueButton.setOnClickListener((v) -> {
                guideToPainting(i + 1);
            });
        } else {
            // Last painting in the tour
            continueButton.setText(R.string.finish_tour);
            continueButton.setBackgroundColor(getResources().getColor(R.color.bright_red));
            continueButton.setOnClickListener((v) -> {
                // Ask the user if they want to take the survey
                Robot.getInstance().cancelAllTtsRequests();
                new TakeSurveyDialogBuilder(this).show();
            });
        }

        // Take the user to the painting
        String nextLocation = painting.getLocation();
        if (!nextLocation.equals(currentLocation)) {
            currentLocation = nextLocation;
            Robot.getInstance().goTo(nextLocation);
        }

        if (volumeIsOn) {
            Robot.getInstance().speak(TtsRequest.create(
                    getTextToSpeak(painting), false, TtsRequest.Language.EN_US));
        }
    }

    /**
     * Generates the text to speak to the user using Temi's TTS
     * @param painting is the Painting to describe to the user
     * @return the string to speak
     */
    private String getTextToSpeak(Painting painting) {
        String artistName = painting.getArtist().getName();
        String medium = painting.getMedium();
        String description = painting.getDescription();

        // TODO: add more speech patterns to choose from (kind of boring at the moment)
        switch (rand.nextInt(2)) {
            case 0:
                return "This work of art by " + artistName + " was created on " + medium
                    + ". Pictured is " + description;
            case 1:
                return "This " + medium + ", created by " + artistName + ", displays " + description;
            default:
                return "";
        }
    }

    /**
     * Updates the painting information on display. This includes the painting image
     * as well as metadata such as artist, year, etc
     * @param painting is the painting to update the information to
     */
    private void updatePaintingInfo(Painting painting) {
        // Update the painting image
        Drawable d = getPaintingImage(painting);
        paintingImage.setImageDrawable(d);

        // Update the painting info displayed
        artistTextView.setText(checkEmpty(painting.getArtist().getName()));
        yearTextView.setText(checkEmpty(painting.getYear()));
        mediumTextView.setText(checkEmpty(painting.getMedium()));
        measurementsTextView.setText(checkEmpty(painting.getMeasurements()));
        descriptionTextView.setText(checkEmpty(painting.getDescription()));
    }

    /**
     * Checks if the given info text is empty and returns the correct info
     * text to use.
     * @param infoText is the info text
     * @return "N/A" if null or empty string, otherwise unchanged infoText
     */
    private String checkEmpty(String infoText) {
        if (infoText == null || infoText.equals("")) {
            return "N/A";
        } else {
            return infoText;
        }
    }

    /**
     * Convenient method for getting a painting image
     * @param p is the painting
     * @return painting drawable image
     */
    private Drawable getPaintingImage(Painting p) {
        return ResourcesCompat.getDrawable(getResources(), p.getImageId(), null);
    }

    /**
     * Creates a popup allowing the user to select which painting to go to
     */
    private void userSelectPainting() {
        Robot.getInstance().cancelAllTtsRequests();
        new SelectPaintingDialogBuilder(this).show();
    }

    /**
     * Loads the artist and painting data
     */
    private void loadData() throws Painting.InvalidArtistException {
        if (paintings != null) {
            // Data is already loaded
            return;
        }

        // Load all of the artists and paintings
        Resources resources = getResources();
        Gson gson = new Gson();
        Artist[] artists = gson.fromJson(new JsonReader(new InputStreamReader(
                resources.openRawResource(R.raw.artists))), Artist[].class);
        paintings = gson.fromJson(new JsonReader(new InputStreamReader(
                resources.openRawResource(R.raw.paintings))), Painting[].class);

        // Get the correct Artist object for each Painting
        Map<Integer, Artist> map = new HashMap<>();
        for (Artist a : artists) {
            map.put(a.getId(), a);
        }
        for (Painting p : paintings) {
            p.setArtist(map.get(p.getArtistId()));
        }
    }

    /**
     * This class is a dialog builder for asking the user if they want to take
     * a survey when the tour is complete
     *
     * @author Gavin Vogt
     */
    private class TakeSurveyDialogBuilder extends AlertDialog.Builder {

        /**
         * Creates and sets up everything inside the dialog for asking
         * the user if they want to take the survey
         * @param context the parent context
         */
        public TakeSurveyDialogBuilder(@NonNull Context context) {
            super(context);
            this.setTitle(R.string.rate_experience);
            this.setMessage(R.string.rate_experience_message);

            // Set up the dialog buttons
            this.setPositiveButton("Take Survey", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Take the user to the survey
                    finish();
                    Intent intent = new Intent(getApplicationContext(), TourSurveyActivity.class);
                    startActivity(intent);

                    // TODO: should make the Temi go back to home base AFTER they complete the survey
                    // Robot.getInstance().goTo(HOME_BASE_LOCATION);
                }
            });
            this.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User does not want to take the survey; quit
                    dialog.cancel();
                    finish();
                    Robot.getInstance().goTo(HOME_BASE_LOCATION);
                }
            });
        }
    }

    /**
     * This class is a dialog builder for asking the user to select which painting
     * they want to go to
     *
     * @author Gavin Vogt
     */
    private class SelectPaintingDialogBuilder extends AlertDialog.Builder {

        /** The dialog created by this builder */
        private AlertDialog dialog;

        /** Number of paintings per row in the dialog */
        private static final int PAINTINGS_PER_ROW = 5;

        /**
         * Creates and sets up everything inside the dialog for selecting the painting
         * @param context the parent context
         */
        public SelectPaintingDialogBuilder(@NonNull Context context) {
            super(context);
            this.setTitle("Select painting to visit");

            // Create the Table of paintings
            TableLayout table = new TableLayout(context);
            int tableMargin = AppUtils.dpToPx(getResources(), 24);
            table.setPaddingRelative(tableMargin, 0, tableMargin, 0);
            table.setLayoutParams(new ViewGroup.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT
            ));

            // Add each group of 5 paintings to a table row
            int i = 0;
            while (i < paintings.length) {
                TableRow row = new TableRow(context);
                row.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                for (int j = 0; j < PAINTINGS_PER_ROW; ++j) {
                    if (i >= paintings.length) {
                        break;
                    }
                    row.addView(createSelectImage(i));
                    ++i;
                }
                table.addView(row);
            }
            this.setView(table);

            this.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        @Override
        public AlertDialog show() {
            // Show the dialog and save the reference so the images can use it
            dialog = this.create();
            dialog.show();
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.65),
                    (int) (getResources().getDisplayMetrics().heightPixels * 0.70)
            );
            return dialog;
        }

        /**
         * Creates an image that the user can click on, closing the dialog
         * and taking them to the corresponding painting
         * @param paintingNum is the index of the painting
         * @return clickable View that displays the painting image
         */
        private ImageView createSelectImage(int paintingNum) {
            // Create the ImageView
            SelectPaintingButton imageView = new SelectPaintingButton(TourActivity.this,
                    paintingNum % PAINTINGS_PER_ROW);
            imageView.setImageDrawable(getPaintingImage(paintings[paintingNum]));

            // Set the click listener
            imageView.setOnClickListener((v) -> {
                dialog.dismiss();
                guideToPainting(paintingNum);
            });

            return imageView;
        }

        /**
         * This class represents an ImageView holding a painting image that can be clicked
         * to select.
         *
         * @author Gavin Vogt
         */
        private class SelectPaintingButton extends androidx.appcompat.widget.AppCompatImageView {

            /**
             * Creates the button for selecting a painting
             * @param context is the parent context
             * @param colNum is the column this button will be in
             */
            public SelectPaintingButton(Context context, int colNum) {
                super(context);
                this.setScaleType(ImageView.ScaleType.FIT_START);
                this.setAdjustViewBounds(true);

                // Set layout / margins
                // Note: this was a pain and images didn't show up until I used
                // TableRow.LayoutParams so the `weight` would work properly
                TableRow.MarginLayoutParams layoutParams = new TableRow.LayoutParams(
                        0, TableRow.LayoutParams.WRAP_CONTENT, 1.0f
                );
                final int margin = AppUtils.dpToPx(getResources(), 8);
                final int leftMargin = (colNum == 0) ? 0 : margin;
                final int rightMargin = (colNum == PAINTINGS_PER_ROW - 1) ? 0 : margin;
                layoutParams.setMargins(leftMargin, margin, rightMargin, margin);
                this.setLayoutParams(layoutParams);

                // Set the touch listener
                this.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return handleTouch(v, event);
                    }
                });
            }

            /**
             * Handles when the image is clicked by shading it
             * @param v is the View that was touched
             * @param event is the event code
             */
            private boolean handleTouch(View v, MotionEvent event) {
                // Yoinked and modified from https://stackoverflow.com/a/14483533
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageView view = (ImageView) v;
                        // overlay is black with transparency of 0x77 (119)
                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL: {
                        ImageView view = (ImageView) v;
                        // clear the overlay
                        view.getDrawable().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }

                return false;
            }
        }

    }

}
