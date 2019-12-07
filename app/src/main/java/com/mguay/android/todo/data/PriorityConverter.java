package com.mguay.android.todo.data;

import androidx.room.TypeConverter;

public class PriorityConverter {
    @TypeConverter
    public static ToDo.Priority toPriority(String priority){
        if (priority == null) return null;

        switch (priority) {
            case "B": return ToDo.Priority.B;
            case "C": return ToDo.Priority.C;
            case "D": return ToDo.Priority.D;
            default: return ToDo.Priority.A;
        }
    }

    @TypeConverter
    public static String fromPriority(ToDo.Priority priority){
        return priority == null ? null : priority.name();
    }
}