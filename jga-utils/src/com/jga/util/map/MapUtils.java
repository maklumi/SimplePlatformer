package com.jga.util.map;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.utils.Logger;
import com.jga.util.Validate;

import java.util.Iterator;

public final class MapUtils {

    // == constants ==
    private static final Logger log = new Logger(MapUtils.class.getName(), Logger.DEBUG);
    private static final String LS = System.getProperty("line.separator");
    private static final String TAB = "\t";

    // == public methods ==
    public static void debugMapProperties(MapProperties mapProperties) {
        Validate.notNull(mapProperties);

        StringBuffer sb = new StringBuffer();
        sb.append("properties").append(LS);

        for (Iterator<String> keyIterator = mapProperties.getKeys(); keyIterator.hasNext(); ) {
            String key = keyIterator.next();
            Object value = mapProperties.get(key);

            String typeName = value.getClass().getSimpleName();
            String keyValueString = "key= " + key + " value= " + value + " type= " + typeName;

            sb.append(TAB);
            sb.append(keyValueString);

            if (keyIterator.hasNext()) {
                sb.append(LS);
            }
        }

        log.debug(sb.toString());
    }

    // == private constructor ==
    private MapUtils() {

    }
}
