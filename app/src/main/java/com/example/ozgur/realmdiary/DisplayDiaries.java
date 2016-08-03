package com.example.ozgur.realmdiary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DisplayDiaries extends ListActivity
{
    private DiaryAdapter adapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_diaries);

        this.adapter = new DiaryAdapter(this);
        this.adapter.setData(this.getEntries());
        this.setListAdapter(this.adapter);

        this.textView = (TextView) this.findViewById(R.id.list_count);
        textView.setText("Count: " + this.adapter.getCount());

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                DiaryEntry entry = (DiaryEntry) adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), DisplayEntry.class);
                intent.putExtra(Constants.TITLE_NAME, entry.getTitle());
                intent.putExtra(Constants.DATE_NAME, entry.getRecordedDate());
                intent.putExtra(Constants.CONTENT_NAME, entry.getContent());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayDiaries.this.addEntries();
            }
        });
    }

    private RealmResults<DiaryEntry> getEntries()
    {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        return realm.where(DiaryEntry.class).findAll();
    }

    private void addEntries()
    {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfiguration);

        DiaryEntry entry = new DiaryEntry();
        entry.setTitle(Constants.SAMPLE_TITLE);
        entry.setContent(Constants.SAMPLE_CONTENT);
        entry.setRecordedDate(System.currentTimeMillis());

        realm.beginTransaction();

        for (int i = 0; i < 1000; i++)
        {
            realm.copyToRealm(entry);
        }

        realm.commitTransaction();

        this.adapter.setData(realm.where(DiaryEntry.class).findAll());

        CharSequence text = "Added 1000 entries";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        startActivity(new Intent(this, DisplayDiaries.class));
        finish();
    }
}
