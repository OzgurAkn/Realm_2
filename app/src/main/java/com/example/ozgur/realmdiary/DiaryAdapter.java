package com.example.ozgur.realmdiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmResults;

/**
 * Created by Ozgur on 28/03/2016.
 */
public class DiaryAdapter extends BaseAdapter
{

    private LayoutInflater inflater;
    private ArrayList<DiaryEntry> entries;

    public DiaryAdapter( Context context)
    {
        inflater = LayoutInflater.from(context);
        entries = new ArrayList<>();
    }

    public void setData(RealmResults<DiaryEntry> results)
    {
        this.entries.addAll(results);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return this.entries.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.entries.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        View view = convertView;
        if ((view == null) || view.getTag() == null)
        {
            //The ViewHolder has not yet been made and must be made
            view = this.inflater.inflate(R.layout.listitem_main, null);

            holder = new ViewHolder();
            holder.setTitle((TextView) view.findViewById(R.id.name));
            holder.setDate((TextView) view.findViewById(R.id.datetext));

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        //Set the required information
        holder.setEntry((DiaryEntry) getItem(position));
        holder.getTitle().setText(holder.getEntry().getTitle());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(holder.getEntry().getRecordedDate());
        holder.getDate().setText(sdf.format(resultdate));

        view.setTag(holder);
        return view;
    }

    private class ViewHolder
    {
        private DiaryEntry entry;
        private TextView title;
        private TextView date;

        public DiaryEntry getEntry()
        {
            return entry;
        }

        public void setEntry(DiaryEntry entry)
        {
            this.entry = entry;
        }

        public TextView getDate()
        {
            return date;
        }

        public void setDate(TextView date)
        {
            this.date = date;
        }

        public TextView getTitle()
        {
            return title;
        }

        public void setTitle(TextView title)
        {
            this.title = title;
        }
    }
}
