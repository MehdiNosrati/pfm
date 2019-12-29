package io.mns.mpfm.db.converters;

import androidx.room.TypeConverter;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

