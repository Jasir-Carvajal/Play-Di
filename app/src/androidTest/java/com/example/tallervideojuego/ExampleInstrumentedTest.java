package com.example.tallervideojuego;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.tallervideojuego.modelo.Api.Api;
import com.example.tallervideojuego.modelo.Api.SyncDB;
import com.example.tallervideojuego.modelo.base.DataBase;
import com.example.tallervideojuego.modelo.entidades.Carta;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void date() {
        assertEquals(0, android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", Calendar.getInstance().getTime()));
    }

    @Test
    public void carta() {
        Carta carta = new Carta();
        carta.setReto("r");
        carta.setTitulo("t");
        carta.setId(0);

        System.out.println(carta.getJson().replaceAll("\"","'"));
        assertEquals(true,carta.getJson() );
    }
    @Test
    public void syncDb() throws InterruptedException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBase.setContext(appContext);
        DataBase.setName("PlaydiDB");

        Api api = new Api();
        api.sincronizar();
        for (int i = 0; i < 8; i++) {
            Thread.sleep(900);
        }
        assertEquals(true,false );

    }

    @Test
    public void makeJson() throws  IOException {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBase.setContext(appContext);
        DataBase.setName("PlaydiDB");

        SyncDB syncDB = new SyncDB();

        assertEquals(true,syncDB.makeJson() );

    }
}