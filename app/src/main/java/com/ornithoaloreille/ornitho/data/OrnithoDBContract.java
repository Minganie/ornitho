package com.ornithoaloreille.ornitho.data;

import android.provider.BaseColumns;

/**
 * Created by Myriam on 2016-10-31.
 */

public class OrnithoDBContract {
    public static final class Groups implements BaseColumns {
        public static final int ID = 0;
        public static final int NAME = 1;
        public static final int ICON = 2;
        public static final int SOUND = 3;
    }

    public static final class Species implements BaseColumns {
        public static final int ID = 0;
        public static final int NAME = 1;
        public static final int DESCRIPTION = 2;
        public static final int SOUND = 3;
        public static final int WIKI = 4;
        public static final int GROUP_ID = 5;
        public static final int GROUP_NAME = 6;
        public static final int GROUP_ICON = 7;
        public static final int GROUP_SOUND = 8;
    }

    public static final class Subgroup implements BaseColumns {
        public static final int ID = 0;
        public static final int DESCRIPTION = 1;
        public static final int GROUP_ID = 2;
        public static final int GROUP_NAME = 3;
        public static final int GROUP_ICON = 4;
        public static final int GROUP_SOUND = 5;
    }
}
