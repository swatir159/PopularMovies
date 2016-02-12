package com.android.rupeeright.popularmovies.DataStorage;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.android.rupeeright.popularmovies.TestUtils.PollingCheck;

import java.util.Map;
import java.util.Set;


/**
 * Created by swatir on 2/9/2016.
 */
public class TestUtilities extends AndroidTestCase {

    static final long TEST_DATE = 1419033600L;  // 1993-01-01

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    /*
        Students: Use this to create some default weather values for your database tests.
     */
    static ContentValues createMovieValues() {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MoviesEntry.COLUMN_ADULT, 1); /* Boolean.TRUE */
        movieValues.put(MovieContract.MoviesEntry.COLUMN_BACKDROP_PATH, "/hAJ3Ea1ZbIjRQKf9ht8LvWwOrSe.jpg");
        movieValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_ID, 15419);
        movieValues.put(MovieContract.MoviesEntry.COLUMN_ORIGINAL_LAN, "hi");
        movieValues.put(MovieContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, "Kabhi Haan Kabhi Naa");
        movieValues.put(MovieContract.MoviesEntry.COLUMN_OVERVIEW, "\"Sunil belongs to a middle-class family, and is intent in pursuing his career with a music group, despite of his dad disapproval. Sunil is also in love with Anna, but Anna does not really love him, but likes him as a friend. Sunil is persistent, but instead Anna openly declares her love for Chris. In order to impress his dad, Sunil forges his examination results, but then later confesses to his family, who receive this news in utter dismay. Will this change Sunil's ways? Will Anna change her mind about Sunil or will she get married to Chris?" );
        movieValues.put(MovieContract.MoviesEntry.COLUMN_POPULARITY, 1);
        movieValues.put(MovieContract.MoviesEntry.COLUMN_POSTER_PATH, "/atZ4SrQv0eB58M5Nr1Tn8Ttp3p0.jpg");
        movieValues.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, TEST_DATE );
        movieValues.put(MovieContract.MoviesEntry.COLUMN_TITLE, "Kabhi Haan Kabhi Naa" );
        movieValues.put(MovieContract.MoviesEntry.COLUMN_VIDEO , 0); /* Boolean.FALSE */
        movieValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE, 6.83);
        movieValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_COUNT, 3);
        return movieValues;
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

}
