package com.unvise.transportbook.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Constants {

    public static final Pattern LICENSE_PLATE_PATTERN_RU = Pattern.compile(
        "^([ABEKMHOPCTYX|АВЕКМНОРСТУХ][0-9]{3}[ABEKMHOPCTYX|АВЕКМНОРСТУХ]{2}_[0-9]{2}RUS)"
    );
    public static final Pattern DATE_PATTERN = Pattern.compile(
            "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$"
    );

    public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
    public static final String EX_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR).format(new Date());

    public static final String DB_DATA_FILE_PATH = "classpath:/db/data.txt";
    public static final String DB_DATA_FILE_SEPARATOR = ",";
}
