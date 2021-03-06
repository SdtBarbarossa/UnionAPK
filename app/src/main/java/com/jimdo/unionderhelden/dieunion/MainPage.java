package com.jimdo.unionderhelden.dieunion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemSelectedListener  {

    public GildenInfos gildenInfos = null;
    public boolean getNewDataSuceed = false;
    public GildenMember selectedGildenMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        startPage startpage = new startPage();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, startpage).commit();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    getNewDataSuceed = getNewData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textSyncBool = findViewById(R.id.textSyncBool);
                            if(getNewDataSuceed) {
                                try {
                                    textSyncBool.setText("Erfolgreich");
                                    TextView textGildenInfos = findViewById(R.id.textGildenInfos);
                                    textGildenInfos.setText("Gildenname : " + gildenInfos.getGildenName()
                                            + System.getProperty("line.separator")
                                            + "Galaktischemacht : " + gildenInfos.getGalaktischeMacht()
                                            + System.getProperty("line.separator")
                                            + "Galaktischemacht Durschnitt = " + gildenInfos.getGalaktischeMachtDurschnitt()
                                            + System.getProperty("line.separator")
                                            + "Gildenrang = " + gildenInfos.getGildenRang()
                                            + System.getProperty("line.separator")
                                            + "Gilden Raidpunkte = " + gildenInfos.getGildenRaidPunkte()
                                            + System.getProperty("line.separator")
                                            + "Gilden Arena Rangdurschnitt = " + gildenInfos.getGildenArenaRangDurschnitt()
                                            + System.getProperty("line.separator")
                                            + "Gilden Sammlungsscore Durschnitt = " + gildenInfos.getGildenSammlungsScoreDurschnitt()
                                            + System.getProperty("line.separator")
                                    );
                                }catch(Exception exep){

                                }
                            }else{
                                if(gildenInfos != null){
                                    textSyncBool.setText("Erfolgreich");
                                    TextView textGildenInfos = findViewById(R.id.textGildenInfos);
                                    textGildenInfos.setText("Gildenname : " + gildenInfos.getGildenName()
                                            + System.getProperty("line.separator")
                                            + "Galaktischemacht : " + gildenInfos.getGalaktischeMacht()
                                            + System.getProperty("line.separator")
                                            + "Galaktischemacht Durschnitt = " + gildenInfos.getGalaktischeMachtDurschnitt()
                                            + System.getProperty("line.separator")
                                            + "Gildenrang = " + gildenInfos.getGildenRang()
                                            + System.getProperty("line.separator")
                                            + "Gilden Raidpunkte = " + gildenInfos.getGildenRaidPunkte()
                                            + System.getProperty("line.separator")
                                            + "Gilden Arena Rangdurschnitt = " + gildenInfos.getGildenArenaRangDurschnitt()
                                            + System.getProperty("line.separator")
                                            + "Gilden Sammlungsscore Durschnitt = " + gildenInfos.getGildenSammlungsScoreDurschnitt()
                                            + System.getProperty("line.separator")
                                    );
                                }
                                textSyncBool.setText("Fehlgeschlagen");
                            }
                        }
                    });

                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lade Daten neu...", Snackbar.LENGTH_LONG)
                        .setAction("HttpGet", null).show();

                Thread thread2 = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            getNewDataSuceed = getNewData();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView textSyncBool = findViewById(R.id.textSyncBool);
                                    if(getNewDataSuceed) {
                                        textSyncBool.setText("Erfolgreich");
                                        TextView textGildenInfos = findViewById(R.id.textGildenInfos);
                                        textGildenInfos.setText("Gildenname : " + gildenInfos.getGildenName()
                                                + System.getProperty("line.separator")
                                                + "Galaktischemacht : " + gildenInfos.getGalaktischeMacht()
                                                + System.getProperty("line.separator")
                                                + "Galaktischemacht Durschnitt = " + gildenInfos.getGalaktischeMachtDurschnitt()
                                                + System.getProperty("line.separator")
                                                + "Gildenrang = " + gildenInfos.getGildenRang()
                                                + System.getProperty("line.separator")
                                                + "Gilden Raidpunkte = " + gildenInfos.getGildenRaidPunkte()
                                                + System.getProperty("line.separator")
                                                + "Gilden Arena Rangdurschnitt = " + gildenInfos.getGildenArenaRangDurschnitt()
                                                + System.getProperty("line.separator")
                                                + "Gilden Sammlungsscore Durschnitt = " + gildenInfos.getGildenSammlungsScoreDurschnitt()
                                                + System.getProperty("line.separator")
                                        );
                                    }else{
                                        textSyncBool.setText("Fehlgeschlagen");
                                    }
                                }
                            });

                            //Your code goes here
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread2.start();
            }
        });

    }

    public boolean getNewData(){

        try{
        String jsonString = getHTML("http://unionderhelden.somee.com/api/Gilden");
        ObjectMapper mapper = new ObjectMapper();
        GildenInfos myProduct = mapper.readValue(jsonString, GildenInfos.class);
        gildenInfos = myProduct;

            // We need an Editor object to make preference changes.
            // All objects are from android.context.Context
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("gildenJson", jsonString);
            editor.commit();


        }catch(Exception exep){
            try {
                // Restore preferences
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String persistJson = settings.getString("gildenJson", "");
                ObjectMapper mapper = new ObjectMapper();
                GildenInfos myProduct = mapper.readValue(persistJson, GildenInfos.class);
                gildenInfos = myProduct;
            }catch(Exception exeption){
                return false;
            }

            return false;
        }

        return true;
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mainpage) {
            showMainPage();
        } else if (id == R.id.nav_charsearch) {
            try {
                showCharSearchUi();
            }catch(Exception exep){}
        } else if (id == R.id.nav_legendary) {
            try {
                showLegendaryUi();
            }catch(Exception ex){}
        } else if (id == R.id.nav_haat) {
            showAatUi();
        } else if (id == R.id.nav_arena) {
            showArenaUi();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showMainPage(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        startPage startpage = new startPage();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, startpage).commit();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textSyncBool = findViewById(R.id.textSyncBool);
                            if(getNewDataSuceed) {
                                try {
                                    textSyncBool.setText("Erfolgreich");
                                    TextView textGildenInfos = findViewById(R.id.textGildenInfos);
                                    textGildenInfos.setText("Gildenname : " + gildenInfos.getGildenName()
                                            + System.getProperty("line.separator")
                                            + "Galaktischemacht : " + gildenInfos.getGalaktischeMacht()
                                            + System.getProperty("line.separator")
                                            + "Galaktischemacht Durschnitt = " + gildenInfos.getGalaktischeMachtDurschnitt()
                                            + System.getProperty("line.separator")
                                            + "Gildenrang = " + gildenInfos.getGildenRang()
                                            + System.getProperty("line.separator")
                                            + "Gilden Raidpunkte = " + gildenInfos.getGildenRaidPunkte()
                                            + System.getProperty("line.separator")
                                            + "Gilden Arena Rangdurschnitt = " + gildenInfos.getGildenArenaRangDurschnitt()
                                            + System.getProperty("line.separator")
                                            + "Gilden Sammlungsscore Durschnitt = " + gildenInfos.getGildenSammlungsScoreDurschnitt()
                                            + System.getProperty("line.separator")
                                    );

                                }catch(Exception exep){

                                }
                            }else{
                                textSyncBool.setText("Fehlgeschlagen");
                            }
                        }
                    });

                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void showCharSearchUi(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        CharSearchFragment charSearch = new CharSearchFragment();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, charSearch).commit();
        fragmentManager.executePendingTransactions();

        Button btSuchen = findViewById(R.id.btSuchen);
        btSuchen.setOnClickListener(this);

        ListView listview = findViewById(R.id.charListView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long l){
                Charakter item = (Charakter)adapter.getItemAtPosition(position);
                Toast toast = Toast.makeText(getApplicationContext(), "Mods kommen bald...", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void showArenaUi(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArenaFragment arenaFragment = new ArenaFragment();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, arenaFragment).commit();
        fragmentManager.executePendingTransactions();

        ArrayList<ArenaTeamListe> findAllTeams = new ArrayList<ArenaTeamListe>();

        for(int i = 0; i < gildenInfos.getMemberListe().size(); i ++){

            ArrayList<Charakter> thisChars = new ArrayList<Charakter>();

            for(int a = 0 ; a < gildenInfos.getMemberListe().get(i).ArenaTeam.size() ; a++){
                Charakter nowChar = gildenInfos.getMemberListe().get(i).findCharByName(gildenInfos.getMemberListe().get(i).ArenaTeam.get(a));
                    thisChars.add(nowChar);
            }

            if(thisChars.size() > 0){
                findAllTeams.add(new ArenaTeamListe(gildenInfos.getMemberListe().get(i).getMemberName(), gildenInfos.getMemberListe().get(i).ArenaRang, thisChars));
            }

        }

        //ALLE Charaktere in der Liste
        ListView listTeamview = findViewById(R.id.arenaTeamListView);
        listTeamview.setAdapter(new ArenaTeamListAdapter(MainPage.this, findAllTeams));

    }


    public void showAatUi(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        AatFragment aatFragment = new AatFragment();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, aatFragment).commit();
        fragmentManager.executePendingTransactions();

        Spinner spinTeam1 = findViewById(R.id.spinnerTeam1);

        ArrayList<haatTeam> haatTeams = new ArrayList<haatTeam>();

        //P1

        //Kylo
        ArrayList<TeamChar> kyloTeam = new ArrayList<TeamChar>();
        TeamChar Kylo = new TeamChar("Kylo Ren");
        kyloTeam.add(Kylo);
        haatTeams.add(new haatTeam("P1: Kylo(Rückzug)",kyloTeam));

        //Savage
        ArrayList<TeamChar> savageTeam = new ArrayList<TeamChar>();
        TeamChar Savage = new TeamChar("Savage Opress");
        savageTeam.add(Savage);
        haatTeams.add(new haatTeam("P1: Savage(Rückzug)",savageTeam));

        //DaHanCLS
        ArrayList<TeamChar> DaHanCLSTeam = new ArrayList<TeamChar>();
        DaHanCLSTeam.add(new TeamChar("Dathcha"));
        DaHanCLSTeam.add(new TeamChar("Commander Luke Skywalker"));
        DaHanCLSTeam.add(new TeamChar("Han Solo"));
        haatTeams.add(new haatTeam("P1: DaHanCLS",DaHanCLSTeam));

        //P2

        //Droids
        ArrayList<TeamChar> DroidsTeam = new ArrayList<TeamChar>();
        DroidsTeam.add(new TeamChar("HK-47"));
        DroidsTeam.add(new TeamChar("IG-88"));
        DroidsTeam.add(new TeamChar("IG-86 Sentinel Droid"));
        DroidsTeam.add(new TeamChar("Jawa Engineer"));
        DroidsTeam.add(new TeamChar("Chief Nebit"));
        haatTeams.add(new haatTeam("P2: Droids",DroidsTeam));


        //Zader
        ArrayList<TeamChar> ZaderTeam = new ArrayList<TeamChar>();
        ZaderTeam.add(new TeamChar("Darth Vader"));
        ZaderTeam.add(new TeamChar("Darth Sidious"));
        ZaderTeam.add(new TeamChar("Tusken Shaman"));
        ZaderTeam.add(new TeamChar("Ewok Elder"));
        ZaderTeam.add(new TeamChar("Captain Phasma"));
        haatTeams.add(new haatTeam("P2: Zidader",ZaderTeam));

        //Wiederstand
        ArrayList<TeamChar> WiederstandTeam = new ArrayList<TeamChar>();
        WiederstandTeam.add(new TeamChar("Finn"));
        WiederstandTeam.add(new TeamChar("Poe Dameron"));
        WiederstandTeam.add(new TeamChar("Rey (Scavenger)"));
        WiederstandTeam.add(new TeamChar("Resistance Pilot"));
        WiederstandTeam.add(new TeamChar("Resistance Trooper"));
        haatTeams.add(new haatTeam("P2: Wiederstand(Keine Legendarys)",WiederstandTeam));

        //Cody
        ArrayList<TeamChar> CodyTeam = new ArrayList<TeamChar>();
        CodyTeam.add(new TeamChar("CC-2224 \"Cody\""));
        CodyTeam.add(new TeamChar("CT-5555 \"Fives\""));
        CodyTeam.add(new TeamChar("CT-21-0408 \"Echo\""));
        CodyTeam.add(new TeamChar("Clone Sergeant - Phase I"));
        CodyTeam.add(new TeamChar("Jawa Scavenger"));
        haatTeams.add(new haatTeam("P2: Cody",CodyTeam));

        //P3

        //ThrawnTeebo
        ArrayList<TeamChar> ThrawnTeeboTeam = new ArrayList<TeamChar>();
        ThrawnTeeboTeam.add(new TeamChar("Teebo"));
        ThrawnTeeboTeam.add(new TeamChar("Grand Admiral Thrawn"));
        ThrawnTeeboTeam.add(new TeamChar("Commander Luke Skywalker"));
        ThrawnTeeboTeam.add(new TeamChar("Han Solo"));
        ThrawnTeeboTeam.add(new TeamChar("Chief Nebit"));
        haatTeams.add(new haatTeam("P3: TeeboLegendary",ThrawnTeeboTeam));

        //ThrawnImperial
        ArrayList<TeamChar> ThrawnImperialTeam = new ArrayList<TeamChar>();
        ThrawnImperialTeam.add(new TeamChar("Grand Admiral Thrawn"));
        ThrawnImperialTeam.add(new TeamChar("Jyn Erso"));
        ThrawnImperialTeam.add(new TeamChar("Shoretrooper"));
        ThrawnImperialTeam.add(new TeamChar("Grand Moff Tarkin"));
        ThrawnImperialTeam.add(new TeamChar("Director Krennic"));
        haatTeams.add(new haatTeam("P3: ImperialThrawn",ThrawnImperialTeam));

        //P2-4

        //Legendarys
        ArrayList<TeamChar> LegendaryTeam = new ArrayList<TeamChar>();
        LegendaryTeam.add(new TeamChar("Grand Admiral Thrawn"));
        LegendaryTeam.add(new TeamChar("Commander Luke Skywalker"));
        LegendaryTeam.add(new TeamChar("Han Solo"));
        LegendaryTeam.add(new TeamChar("BB-8"));
        LegendaryTeam.add(new TeamChar("Ezra Bridger"));
        haatTeams.add(new haatTeam("P2-4: 32M DMG",LegendaryTeam));

        //Rancor

        //Thrawnisierer
        ArrayList<TeamChar> RancorThrawnTeam = new ArrayList<TeamChar>();
        RancorThrawnTeam.add(new TeamChar("Grand Admiral Thrawn"));
        RancorThrawnTeam.add(new TeamChar("Jyn Erso"));
        RancorThrawnTeam.add(new TeamChar("Grand Moff Tarkin"));
        RancorThrawnTeam.add(new TeamChar("Director Krennic"));
        RancorThrawnTeam.add(new TeamChar("Shoretrooper"));
        haatTeams.add(new haatTeam("Rancor: Thrawnisierer",RancorThrawnTeam));

        //Zader
        ArrayList<TeamChar> RancorZaderTeam = new ArrayList<TeamChar>();
        RancorZaderTeam.add(new TeamChar("Darth Vader"));
        RancorZaderTeam.add(new TeamChar("Jyn Erso"));
        RancorZaderTeam.add(new TeamChar("Grand Moff Tarkin"));
        RancorZaderTeam.add(new TeamChar("TIE Fighter Pilot"));
        RancorZaderTeam.add(new TeamChar("CT-7567 \"Rex\""));
        haatTeams.add(new haatTeam("Rancor: Zader",RancorZaderTeam));

        //OldSchool
        ArrayList<TeamChar> RancorOldSchoolTeam = new ArrayList<TeamChar>();
        RancorOldSchoolTeam.add(new TeamChar("Teebo"));
        RancorOldSchoolTeam.add(new TeamChar("Qui-Gon Jinn"));
        RancorOldSchoolTeam.add(new TeamChar("TIE Fighter Pilot"));
        RancorOldSchoolTeam.add(new TeamChar("CT-5555 \"Fives\""));
        RancorOldSchoolTeam.add(new TeamChar("CT-7567 \"Rex\""));
        haatTeams.add(new haatTeam("Rancor: Old school",RancorOldSchoolTeam));

        ArrayAdapter adapterHaat = new ArrayAdapter(this,android.R.layout.simple_spinner_item, haatTeams);
        spinTeam1.setAdapter(adapterHaat);
        spinTeam1.setOnItemSelectedListener(this);

    }

    public void showLegendaryUi(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        LegendaryFragment legendary = new LegendaryFragment();
        fragmentManager.beginTransaction().replace(R.id.mainFrame, legendary).commit();
        fragmentManager.executePendingTransactions();

        Spinner spinMember = findViewById(R.id.spinnerMember);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, gildenInfos.getMemberListe());
        spinMember.setAdapter(adapter);

        Spinner spinLegendary = findViewById(R.id.spinnerTeam);

        ArrayList<String> Legendarys = new ArrayList<String>();
        Legendarys.add("Commander Luke Skywalker");
        Legendarys.add("Rey (Jedi Schülerin)");
        Legendarys.add("Thrawn");
        Legendarys.add("R2-D2");
        Legendarys.add("BB8");

        ArrayAdapter adapterLegendary = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Legendarys);
        spinLegendary.setAdapter(adapterLegendary);
        spinLegendary.setOnItemSelectedListener(this);
        spinMember.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerTeam1)
        {
            ArrayList<ArrayList<Charakter>> findAllTeams = new ArrayList<ArrayList<Charakter>>();

            for(int i = 0; i < gildenInfos.getMemberListe().size(); i ++){

                ArrayList<Charakter> thisChars = new ArrayList<Charakter>();
                haatTeam nowTeam = (haatTeam)spinner.getSelectedItem();
                for(int a = 0 ; a < nowTeam.teamChars.size() ; a++){
                    Charakter nowChar = gildenInfos.getMemberListe().get(i).findCharByName(nowTeam.teamChars.get(a).charName);
                    if(nowChar != null){
                        nowChar.Besitzer = gildenInfos.getMemberListe().get(i).getMemberName();
                        thisChars.add(nowChar);
                    }
                }

                if(thisChars.size() > 0){
                    findAllTeams.add(thisChars);
                }

            }

            //ALLE Charaktere in der Liste
            ListView listTeamview = findViewById(R.id.teamListView);
            listTeamview.setAdapter(new TeamListAdapter(MainPage.this, findAllTeams));
        }
        else if(spinner.getId() == R.id.spinnerMember || spinner.getId() == R.id.spinnerTeam)
        {
            Spinner spinTeam = (Spinner)findViewById(R.id.spinnerTeam);
            String selectedTeam = (String) spinTeam.getSelectedItem();

            Spinner spinMember = (Spinner)findViewById(R.id.spinnerMember);
            MemberListe selectedMember = (MemberListe) spinMember.getSelectedItem();

            View charListView = (View) findViewById(R.id.charList);

            View char1View = (View) charListView.findViewById(R.id.char1);
            View char2View = (View) charListView.findViewById(R.id.char2);
            View char3View = (View) charListView.findViewById(R.id.char3);
            View char4View = (View) charListView.findViewById(R.id.char4);
            View char5View = (View) charListView.findViewById(R.id.char5);

            ImageView char1Image = char1View.findViewById(R.id.charImage1);
            char1Image.setImageResource(R.drawable.wallpaper);
            ImageView char2Image = char2View.findViewById(R.id.charImage1);
            char2Image.setImageResource(R.drawable.wallpaper);
            ImageView char3Image = char3View.findViewById(R.id.charImage1);
            char3Image.setImageResource(R.drawable.wallpaper);
            ImageView char4Image = char4View.findViewById(R.id.charImage1);
            char4Image.setImageResource(R.drawable.wallpaper);
            ImageView char5Image = char5View.findViewById(R.id.charImage1);
            char5Image.setImageResource(R.drawable.wallpaper);

            switch (selectedTeam) {
                case "Commander Luke Skywalker":

                    ArrayList<Charakter> CLSList = new ArrayList<Charakter>();
                    Charakter Han = selectedMember.findCharByName("Stormtrooper Han");
                    if(Han != null) {
                        CLSList.add(Han);
                    }
                    Charakter Chewie = selectedMember.findCharByName("Obi-Wan Kenobi (Old Ben)");
                    if(Chewie != null) {
                        CLSList.add(Chewie);
                    }
                    Charakter Leia = selectedMember.findCharByName("Princess Leia");
                    if(Leia != null) {
                        CLSList.add(Leia);
                    }
                    Charakter Luke = selectedMember.findCharByName("Luke Skywalker (Farmboy)");
                    if(Luke != null) {
                        CLSList.add(Luke);
                    }
                    Charakter Rtwo = selectedMember.findCharByName("R2-D2");
                    if(Rtwo != null) {
                        CLSList.add(Rtwo);
                    }

                    for(int i = 0; i < CLSList.size() ; i++){

                        Charakter charac = CLSList.get(i);

                        View nowView = char1View;

                        switch(i){
                            case 0:
                                nowView = char1View;
                                break;
                            case 1:
                                nowView = char2View;
                                break;
                            case 2:
                                nowView = char3View;
                                break;
                            case 3:
                                nowView = char4View;
                                break;
                            case 4:
                                nowView = char5View;
                                break;
                            default:
                                continue;
                        }

                        ImageView imgChar = (ImageView)nowView.findViewById(R.id.charImage1);

                        switch(charac.getName()){
                            case "Obi-Wan Kenobi (Old Ben)":
                                imgChar.setImageResource(R.drawable.charui_obiwanep4);
                                break;
                            case "Stormtrooper Han":
                                imgChar.setImageResource(R.drawable.charui_trooperstorm_han);
                                break;
                            case "Princess Leia":
                                imgChar.setImageResource(R.drawable.charui_leia_princess);
                                break;
                            case "Luke Skywalker (Farmboy)":
                                imgChar.setImageResource(R.drawable.charui_luke_ep4);
                                break;
                            case "R2-D2":
                                imgChar.setImageResource(R.drawable.charui_astromech_r2d2);
                                break;
                            default:
                                break;
                        }

                        ImageView gearImage = (ImageView)nowView.findViewById(R.id.gearImage1);

                        switch(CLSList.get(i).getGear()){
                            case 0:
                                break;
                            case 1:
                                gearImage.setImageResource(R.drawable.gearicong1);
                                break;
                            case 2:
                                gearImage.setImageResource(R.drawable.gearicong2);
                                break;
                            case 3:
                                gearImage.setImageResource(R.drawable.gearicong3);
                                break;
                            case 4:
                                gearImage.setImageResource(R.drawable.gearicong4);
                                break;
                            case 5:
                                gearImage.setImageResource(R.drawable.gearicong5);
                                break;
                            case 6:
                                gearImage.setImageResource(R.drawable.gearicong6);
                                break;
                            case 7:
                                gearImage.setImageResource(R.drawable.gearicong7);
                                break;
                            case 8:
                                gearImage.setImageResource(R.drawable.gearicong8);
                                break;
                            case 9:
                                gearImage.setImageResource(R.drawable.gearicong9);
                                break;
                            case 10:
                                gearImage.setImageResource(R.drawable.gearicong10);
                                break;
                            case 11:
                                gearImage.setImageResource(R.drawable.gearicong11);
                                break;
                            case 12:
                                gearImage.setImageResource(R.drawable.gearicong12);
                                break;
                            default:
                                break;
                        }


                        ArrayList<ImageView> imageStarList = new ArrayList<ImageView>();
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar11));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar21));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar31));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar41));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar51));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar61));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar71));

                        int stars = CLSList.get(i).getSterne();

                        for(int z = 0; z < imageStarList.size() ; z++) {
                            if(z < stars) {
                                imageStarList.get(z).setImageResource(R.drawable.star);
                            }else{
                                imageStarList.get(z).setImageResource(R.drawable.nostar);
                            }
                        }

                        TextView txtGear = (TextView) nowView.findViewById(R.id.gear);
                        txtGear.setText(CLSList.get(i).getGear().toString());

                        TextView txtperfektheit = (TextView) nowView.findViewById(R.id.perfektheit1);
                        txtperfektheit.setText(CLSList.get(i).getPerfekt().toString());

                    }

                    break;
                case "Rey (Jedi Schülerin)":
                    String[] ReyNames = { "Rey (Scavenger)","Finn","BB-8","Veteran Smuggler Han Solo","Veteran Smuggler Chewbacca" };

                    ArrayList<Charakter> ReyList = new ArrayList<Charakter>();

                    for(int i = 0 ; i < ReyNames.length; i++){
                        Charakter charac = selectedMember.findCharByName(ReyNames[i]);
                        if(charac != null){
                            ReyList.add(charac);
                        }
                    }

                    Collections.sort(ReyList, new Comparator<Charakter>() {
                        @Override public int compare(Charakter p1, Charakter p2) {
                            return p2.getPerfekt() - p1.getPerfekt();
                        }
                    });

                    if(ReyList.size() > 4){
                        ArrayList<Charakter> ReyFive = new ArrayList<Charakter>();
                        ReyFive.add(ReyList.get(0));
                        ReyFive.add(ReyList.get(1));
                        ReyFive.add(ReyList.get(2));
                        ReyFive.add(ReyList.get(3));
                        ReyFive.add(ReyList.get(4));
                        ReyList = ReyFive;
                    }

                    for(int i = 0; i < ReyList.size() ; i++){

                        Charakter charac = ReyList.get(i);

                        View nowView = char1View;

                        switch(i){
                            case 0:
                                nowView = char1View;
                                break;
                            case 1:
                                nowView = char2View;
                                break;
                            case 2:
                                nowView = char3View;
                                break;
                            case 3:
                                nowView = char4View;
                                break;
                            case 4:
                                nowView = char5View;
                                break;
                            default:
                                continue;
                        }

                        ImageView imgChar = (ImageView)nowView.findViewById(R.id.charImage1);

                        switch(charac.getName()){
                            case "Rey (Scavenger)":
                                imgChar.setImageResource(R.drawable.charui_reyjakku);
                                break;
                            case "Finn":
                                imgChar.setImageResource(R.drawable.charui_finnjakku);
                                break;
                            case "BB-8":
                                imgChar.setImageResource(R.drawable.charui_bb8);
                                break;
                            case "Veteran Smuggler Han Solo":
                                imgChar.setImageResource(R.drawable.charui_tfa_han);
                                break;
                            case "Veteran Smuggler Chewbacca":
                                imgChar.setImageResource(R.drawable.charui_tfa_chewbacca);
                                break;
                            default:
                                break;
                        }

                        ImageView gearImage = (ImageView)nowView.findViewById(R.id.gearImage1);

                        switch(ReyList.get(i).getGear()){
                            case 0:
                                break;
                            case 1:
                                gearImage.setImageResource(R.drawable.gearicong1);
                                break;
                            case 2:
                                gearImage.setImageResource(R.drawable.gearicong2);
                                break;
                            case 3:
                                gearImage.setImageResource(R.drawable.gearicong3);
                                break;
                            case 4:
                                gearImage.setImageResource(R.drawable.gearicong4);
                                break;
                            case 5:
                                gearImage.setImageResource(R.drawable.gearicong5);
                                break;
                            case 6:
                                gearImage.setImageResource(R.drawable.gearicong6);
                                break;
                            case 7:
                                gearImage.setImageResource(R.drawable.gearicong7);
                                break;
                            case 8:
                                gearImage.setImageResource(R.drawable.gearicong8);
                                break;
                            case 9:
                                gearImage.setImageResource(R.drawable.gearicong9);
                                break;
                            case 10:
                                gearImage.setImageResource(R.drawable.gearicong10);
                                break;
                            case 11:
                                gearImage.setImageResource(R.drawable.gearicong11);
                                break;
                            case 12:
                                gearImage.setImageResource(R.drawable.gearicong12);
                                break;
                            default:
                                break;
                        }


                        ArrayList<ImageView> imageStarList = new ArrayList<ImageView>();
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar11));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar21));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar31));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar41));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar51));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar61));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar71));

                        int stars = ReyList.get(i).getSterne();

                        for(int z = 0; z < imageStarList.size() ; z++) {
                            if(z < stars) {
                                imageStarList.get(z).setImageResource(R.drawable.star);
                            }else{
                                imageStarList.get(z).setImageResource(R.drawable.nostar);
                            }
                        }

                        TextView txtGear = (TextView) nowView.findViewById(R.id.gear);
                        txtGear.setText(ReyList.get(i).getGear().toString());

                        TextView txtperfektheit = (TextView) nowView.findViewById(R.id.perfektheit1);
                        txtperfektheit.setText(ReyList.get(i).getPerfekt().toString());
                    }

                    break;
                case "BB8":
                    String[] resistanceNames = { "Captain Phasma","First Order Officer","First Order SF TIE Pilot","First Order Stormtrooper","First Order TIE Pilot","Kylo Ren","Kylo Ren (Unmasked)" };

                    ArrayList<Charakter> bb8List = new ArrayList<Charakter>();

                    for(int i = 0 ; i < resistanceNames.length; i++){
                        Charakter charac = selectedMember.findCharByName(resistanceNames[i]);
                        if(charac != null){
                            bb8List.add(charac);
                        }
                    }

                    Collections.sort(bb8List, new Comparator<Charakter>() {
                        @Override public int compare(Charakter p1, Charakter p2) {
                            return p2.getPerfekt() - p1.getPerfekt();
                        }
                    });

                    if(bb8List.size() > 4){
                        ArrayList<Charakter> bb8Five = new ArrayList<Charakter>();
                        bb8Five.add(bb8List.get(0));
                        bb8Five.add(bb8List.get(1));
                        bb8Five.add(bb8List.get(2));
                        bb8Five.add(bb8List.get(3));
                        bb8Five.add(bb8List.get(4));
                        bb8Five = bb8Five;
                    }

                    for(int i = 0; i < bb8List.size() ; i++){

                        Charakter charac = bb8List.get(i);

                        View nowView = char1View;

                        switch(i){
                            case 0:
                                nowView = char1View;
                                break;
                            case 1:
                                nowView = char2View;
                                break;
                            case 2:
                                nowView = char3View;
                                break;
                            case 3:
                                nowView = char4View;
                                break;
                            case 4:
                                nowView = char5View;
                                break;
                            default:
                                continue;
                        }

                        ImageView imgChar = (ImageView)nowView.findViewById(R.id.charImage1);

                        switch(charac.getName()){
                            case "Captain Phasma":
                                imgChar.setImageResource(R.drawable.charui_phasma);
                                break;
                            case "First Order Officer":
                                imgChar.setImageResource(R.drawable.charui_firstorderofficer);
                                break;
                            case "First Order SF TIE Pilot":
                                imgChar.setImageResource(R.drawable.charui_firstorder_pilot);
                                break;
                            case "First Order Stormtrooper":
                                imgChar.setImageResource(R.drawable.charui_firstordertrooper);
                                break;
                            case "First Order TIE Pilot":
                                imgChar.setImageResource(R.drawable.charui_firstordertiepilot);
                                break;
                            case "Kylo Ren":
                                imgChar.setImageResource(R.drawable.charui_kyloren);
                                break;
                            case "Kylo Ren (Unmasked)":
                                imgChar.setImageResource(R.drawable.charui_kylo_unmasked);
                                break;
                            default:
                                break;
                        }

                        ImageView gearImage = (ImageView)nowView.findViewById(R.id.gearImage1);

                        switch(bb8List.get(i).getGear()){
                            case 0:
                                break;
                            case 1:
                                gearImage.setImageResource(R.drawable.gearicong1);
                                break;
                            case 2:
                                gearImage.setImageResource(R.drawable.gearicong2);
                                break;
                            case 3:
                                gearImage.setImageResource(R.drawable.gearicong3);
                                break;
                            case 4:
                                gearImage.setImageResource(R.drawable.gearicong4);
                                break;
                            case 5:
                                gearImage.setImageResource(R.drawable.gearicong5);
                                break;
                            case 6:
                                gearImage.setImageResource(R.drawable.gearicong6);
                                break;
                            case 7:
                                gearImage.setImageResource(R.drawable.gearicong7);
                                break;
                            case 8:
                                gearImage.setImageResource(R.drawable.gearicong8);
                                break;
                            case 9:
                                gearImage.setImageResource(R.drawable.gearicong9);
                                break;
                            case 10:
                                gearImage.setImageResource(R.drawable.gearicong10);
                                break;
                            case 11:
                                gearImage.setImageResource(R.drawable.gearicong11);
                                break;
                            case 12:
                                gearImage.setImageResource(R.drawable.gearicong12);
                                break;
                            default:
                                break;
                        }


                        ArrayList<ImageView> imageStarList = new ArrayList<ImageView>();
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar11));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar21));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar31));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar41));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar51));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar61));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar71));

                        int stars = bb8List.get(i).getSterne();

                        for(int z = 0; z < imageStarList.size() ; z++) {
                            if(z < stars) {
                                imageStarList.get(z).setImageResource(R.drawable.star);
                            }else{
                                imageStarList.get(z).setImageResource(R.drawable.nostar);
                            }
                        }

                        TextView txtGear = (TextView) nowView.findViewById(R.id.gear);
                        txtGear.setText(bb8List.get(i).getGear().toString());

                        TextView txtperfektheit = (TextView) nowView.findViewById(R.id.perfektheit1);
                        txtperfektheit.setText(bb8List.get(i).getPerfekt().toString());
                    }

                    break;
                case "R2-D2":
                    String[] empireNames = {"Colonel Starck", "Darth Vader", "Death Trooper", "Director Krennic", "Emperor Palpatine", "Gar Saxon","General Veers","Grand Admiral Thrawn","Grand Moff Tarkin","Imperial Probe Droid","Imperial Super Commando","Magmatrooper","Royal Guard","Shoretrooper","Snowtrooper","Stormtrooper","TIE Fighter Pilot"};

                    ArrayList<Charakter> r2List = new ArrayList<Charakter>();

                    for(int i = 0 ; i < empireNames.length; i++){
                        Charakter charac = selectedMember.findCharByName(empireNames[i]);
                        if(charac != null){
                            r2List.add(charac);
                        }
                    }

                    Collections.sort(r2List, new Comparator<Charakter>() {
                        @Override public int compare(Charakter p1, Charakter p2) {
                            return p2.getPerfekt() - p1.getPerfekt();
                        }
                    });

                    if(r2List.size() > 4){
                        ArrayList<Charakter> r2Five = new ArrayList<Charakter>();
                        r2Five.add(r2List.get(0));
                        r2Five.add(r2List.get(1));
                        r2Five.add(r2List.get(2));
                        r2Five.add(r2List.get(3));
                        r2Five.add(r2List.get(4));
                        r2Five = r2Five;
                    }

                    for(int i = 0; i < r2List.size() ; i++){

                        Charakter charac = r2List.get(i);

                        View nowView = char1View;

                        switch(i){
                            case 0:
                                nowView = char1View;
                                break;
                            case 1:
                                nowView = char2View;
                                break;
                            case 2:
                                nowView = char3View;
                                break;
                            case 3:
                                nowView = char4View;
                                break;
                            case 4:
                                nowView = char5View;
                                break;
                            default:
                                continue;
                        }

                        ImageView imgChar = (ImageView)nowView.findViewById(R.id.charImage1);

                        switch(charac.getName()){
                            case "Colonel Starck":
                                imgChar.setImageResource(R.drawable.charui_colonel_stark);
                                break;
                            case "Darth Vader":
                                imgChar.setImageResource(R.drawable.charui_vader);
                                break;
                            case "Death Trooper":
                                imgChar.setImageResource(R.drawable.charui_trooperdeath);
                                break;
                            case "Director Krennic":
                                imgChar.setImageResource(R.drawable.charui_krennic);
                                break;
                            case "Emperor Palpatine":
                                imgChar.setImageResource(R.drawable.charui_palpatineemperor);
                                break;
                            case "Gar Saxon":
                                imgChar.setImageResource(R.drawable.charui_gar_saxon);
                                break;
                            case "General Veers":
                                imgChar.setImageResource(R.drawable.charui_veers);
                                break;
                            case "Grand Admiral Thrawn":
                                imgChar.setImageResource(R.drawable.charui_thrawn);
                                break;
                            case "Grand Moff Tarkin":
                                imgChar.setImageResource(R.drawable.charui_tarkinadmiral);
                                break;
                            case "Imperial Probe Droid":
                                imgChar.setImageResource(R.drawable.charui_probedroid);
                                break;
                            case "Imperial Super Commando":
                                imgChar.setImageResource(R.drawable.charui_imperial_super_commando);
                                break;
                            case "Magmatrooper":
                                imgChar.setImageResource(R.drawable.charui_trooperstorm_magma);
                                break;
                            case "Royal Guard":
                                imgChar.setImageResource(R.drawable.charui_royalguard);
                                break;
                            case "Shoretrooper":
                                imgChar.setImageResource(R.drawable.charui_troopershore);
                                break;
                            case "Snowtrooper":
                                imgChar.setImageResource(R.drawable.charui_troopersnow);
                                break;
                            case "Stormtrooper":
                                imgChar.setImageResource(R.drawable.charui_trooperstorm);
                                break;
                            case "TIE Fighter Pilot":
                                imgChar.setImageResource(R.drawable.charui_tiepilot);
                                break;
                            default:
                                break;
                        }

                        ImageView gearImage = (ImageView)nowView.findViewById(R.id.gearImage1);

                        switch(r2List.get(i).getGear()){
                            case 0:
                                break;
                            case 1:
                                gearImage.setImageResource(R.drawable.gearicong1);
                                break;
                            case 2:
                                gearImage.setImageResource(R.drawable.gearicong2);
                                break;
                            case 3:
                                gearImage.setImageResource(R.drawable.gearicong3);
                                break;
                            case 4:
                                gearImage.setImageResource(R.drawable.gearicong4);
                                break;
                            case 5:
                                gearImage.setImageResource(R.drawable.gearicong5);
                                break;
                            case 6:
                                gearImage.setImageResource(R.drawable.gearicong6);
                                break;
                            case 7:
                                gearImage.setImageResource(R.drawable.gearicong7);
                                break;
                            case 8:
                                gearImage.setImageResource(R.drawable.gearicong8);
                                break;
                            case 9:
                                gearImage.setImageResource(R.drawable.gearicong9);
                                break;
                            case 10:
                                gearImage.setImageResource(R.drawable.gearicong10);
                                break;
                            case 11:
                                gearImage.setImageResource(R.drawable.gearicong11);
                                break;
                            case 12:
                                gearImage.setImageResource(R.drawable.gearicong12);
                                break;
                            default:
                                break;
                        }


                        ArrayList<ImageView> imageStarList = new ArrayList<ImageView>();
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar11));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar21));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar31));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar41));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar51));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar61));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar71));

                        int stars = r2List.get(i).getSterne();

                        for(int z = 0; z < imageStarList.size() ; z++) {
                            if(z < stars) {
                                imageStarList.get(z).setImageResource(R.drawable.star);
                            }else{
                                imageStarList.get(z).setImageResource(R.drawable.nostar);
                            }
                        }

                        TextView txtGear = (TextView) nowView.findViewById(R.id.gear);
                        txtGear.setText(r2List.get(i).getGear().toString());

                        TextView txtperfektheit = (TextView) nowView.findViewById(R.id.perfektheit1);
                        txtperfektheit.setText(r2List.get(i).getPerfekt().toString());
                    }

                    break;
                case "Thrawn":

                    ArrayList<Charakter> phoenixList = new ArrayList<Charakter>();
                    Charakter Hera = selectedMember.findCharByName("Hera Syndulla");
                    if(Hera != null) {
                        phoenixList.add(Hera);
                    }
                    Charakter Ezra = selectedMember.findCharByName("Ezra Bridger");
                    if(Ezra != null) {
                        phoenixList.add(Ezra);
                    }
                    Charakter Kanan = selectedMember.findCharByName("Kanan Jarrus");
                    if(Kanan != null) {
                        phoenixList.add(Kanan);
                    }
                    Charakter Garab = selectedMember.findCharByName("Garazeb \"Zeb\" Orrelios");
                    if(Garab != null) {
                        phoenixList.add(Garab);
                    }
                    Charakter Sabine = selectedMember.findCharByName("Sabine Wren");
                    if(Sabine != null) {
                        phoenixList.add(Sabine);
                    }
                    Charakter Chopper = selectedMember.findCharByName("Chopper");
                    if(Chopper != null) {
                        phoenixList.add(Chopper);
                    }

                    if(phoenixList.size() > 4){

                        int indexOfLowest = 0;
                        int lowest = 100;

                        for(int i = 0; i < phoenixList.size() ; i++){
                            if(phoenixList.get(i).getPerfekt()<lowest){
                                indexOfLowest = i;
                                lowest = phoenixList.get(i).getPerfekt();
                            }
                        }

                        phoenixList.remove(indexOfLowest);
                    }

                    for(int i = 0; i < phoenixList.size() ; i++){

                        Charakter charac = phoenixList.get(i);

                        View nowView = char1View;

                        switch(i){
                            case 0:
                                nowView = char1View;
                                break;
                            case 1:
                                nowView = char2View;
                                break;
                            case 2:
                                nowView = char3View;
                                break;
                            case 3:
                                nowView = char4View;
                                break;
                            case 4:
                                nowView = char5View;
                                break;
                            default:
                                continue;
                        }

                        ImageView imgChar = (ImageView)nowView.findViewById(R.id.charImage1);

                        switch(charac.getName()){
                            case "Hera Syndulla":
                                imgChar.setImageResource(R.drawable.charui_hera_s3);
                                break;
                            case "Ezra Bridger":
                                imgChar.setImageResource(R.drawable.charui_ezra_s3);
                                break;
                            case "Kanan Jarrus":
                                imgChar.setImageResource(R.drawable.charui_kanan_s3);
                                break;
                            case "Garazeb \"Zeb\" Orrelios":
                                imgChar.setImageResource(R.drawable.charui_zeb_s3);
                                break;
                            case "Sabine Wren":
                                imgChar.setImageResource(R.drawable.charui_sabine_s3);
                                break;
                            case "Chopper":
                                imgChar.setImageResource(R.drawable.charui_chopper);
                                break;
                            default:
                                break;
                        }

                        ImageView gearImage = (ImageView)nowView.findViewById(R.id.gearImage1);

                        switch(phoenixList.get(i).getGear()){
                            case 0:
                                break;
                            case 1:
                                gearImage.setImageResource(R.drawable.gearicong1);
                                break;
                            case 2:
                                gearImage.setImageResource(R.drawable.gearicong2);
                                break;
                            case 3:
                                gearImage.setImageResource(R.drawable.gearicong3);
                                break;
                            case 4:
                                gearImage.setImageResource(R.drawable.gearicong4);
                                break;
                            case 5:
                                gearImage.setImageResource(R.drawable.gearicong5);
                                break;
                            case 6:
                                gearImage.setImageResource(R.drawable.gearicong6);
                                break;
                            case 7:
                                gearImage.setImageResource(R.drawable.gearicong7);
                                break;
                            case 8:
                                gearImage.setImageResource(R.drawable.gearicong8);
                                break;
                            case 9:
                                gearImage.setImageResource(R.drawable.gearicong9);
                                break;
                            case 10:
                                gearImage.setImageResource(R.drawable.gearicong10);
                                break;
                            case 11:
                                gearImage.setImageResource(R.drawable.gearicong11);
                                break;
                            case 12:
                                gearImage.setImageResource(R.drawable.gearicong12);
                                break;
                            default:
                                break;
                        }


                        ArrayList<ImageView> imageStarList = new ArrayList<ImageView>();
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar11));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar21));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar31));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar41));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar51));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar61));
                        imageStarList.add((ImageView) nowView.findViewById(R.id.nostar71));

                        int stars = phoenixList.get(i).getSterne();

                        for(int z = 0; z < imageStarList.size() ; z++) {
                            if(z < stars) {
                                imageStarList.get(z).setImageResource(R.drawable.star);
                            }else{
                                imageStarList.get(z).setImageResource(R.drawable.nostar);
                            }
                        }

                        TextView txtGear = (TextView) nowView.findViewById(R.id.gear);
                        txtGear.setText(phoenixList.get(i).getGear().toString());

                        TextView txtperfektheit = (TextView) nowView.findViewById(R.id.perfektheit1);
                        txtperfektheit.setText(phoenixList.get(i).getPerfekt().toString());

                    }

                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        // sometimes you need nothing here
    }

    @Override
    public void onClick(View v) {

        if(v == findViewById(R.id.btSuchen)) {

            ArrayList<Charakter> charList = new ArrayList<Charakter>();

            EditText ediText = findViewById(R.id.charSearchText);
            String searchName = ediText.getText().toString();

            for (int i = 0; i < gildenInfos.getMemberListe().size(); i++) {

                for (int z = 0; z < gildenInfos.getMemberListe().get(i).getCharakter().size(); z++) {

                    Charakter charNow = gildenInfos.getMemberListe().get(i).getCharakter().get(z);
                    if (charNow.getName().contains(searchName)) {
                        charNow.Besitzer = gildenInfos.getMemberListe().get(i).getMemberName();
                        charList.add(charNow);
                    }

                }

            }

            //ALLE Charaktere in der Liste
            ListView listview = findViewById(R.id.charListView);
            listview.setAdapter(new CharListAdapter(MainPage.this, charList));
        }
    }
}
