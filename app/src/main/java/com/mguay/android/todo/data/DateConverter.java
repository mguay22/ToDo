package com.mguay.android.todo.data;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public Long fromDate(Date date){
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}