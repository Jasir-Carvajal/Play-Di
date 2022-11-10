package com.example.tallervideojuego;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.tallervideojuego.modelo.Api.SyncDB;
import com.example.tallervideojuego.modelo.base.DataBase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws  IOException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBase.setContext(appContext);
        DataBase.setName("PlaydiDB");
        //Api api = new Api();

        SyncDB syncDB = new SyncDB();




        assertEquals(true, syncDB.makeJson() );

    }
}