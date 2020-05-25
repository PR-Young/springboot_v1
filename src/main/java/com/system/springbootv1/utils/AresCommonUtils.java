package com.system.springbootv1.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @description:
 * @author: yy 2020/05/15
 **/
public class AresCommonUtils {

    public static GsonBuilder builder = new GsonBuilder();
    public static Gson gson = builder.serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    static {
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                Date temp = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    temp = new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                } catch (Exception e) {
                    temp = new Date(jsonElement.getAsJsonPrimitive().getAsString());
                }
                return temp;
            }
        });
    }

    public static boolean isNotEmpty(Object obj) {
        if (obj instanceof Map) {
            return null != obj && ((Map) obj).size() > 0;
        } else if (obj instanceof Collection) {
            return null != obj && ((Collection) obj).size() > 0;
        } else if (obj instanceof String) {
            return null != obj && !"".equals(((String) obj).trim());
        } else {
            return null != obj;
        }
    }

    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
    }

}
