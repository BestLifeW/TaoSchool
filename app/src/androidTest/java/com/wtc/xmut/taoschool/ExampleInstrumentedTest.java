package com.wtc.xmut.taoschool;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.wtc.xmut.taoschool.utils.PrefUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private String string;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.wtc.xmut.taoschool", appContext.getPackageName());

        string = PrefUtils.getString(appContext, PrefUtils.USER_NUMBER, "");
        Log.i(TAG, "useAppContext: "+string);
        assertEquals("admin", string);
    }
}
