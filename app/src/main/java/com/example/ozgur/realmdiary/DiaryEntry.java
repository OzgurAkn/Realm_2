package com.example.ozgur.realmdiary;

import io.realm.RealmObject;

/**
 * Created by Ozgur on 28/03/2016.
 */
public class DiaryEntry extends RealmObject
{
    private String title;
    private String content;
    private long recordedDate;

    public long getRecordedDate()
    {
        return recordedDate;
    }

    public void setRecordedDate(long recordedDate)
    {
        this.recordedDate = recordedDate;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
