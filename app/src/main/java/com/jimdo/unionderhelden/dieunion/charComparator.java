package com.jimdo.unionderhelden.dieunion;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Alle on 06.12.2017.
 */

public class charComparator implements Comparator<Charakter> {

        public int compare(Charakter left, Charakter right)
        {
            if (left.getPerfekt() > right.getPerfekt()) return -1;
            if (left.getPerfekt() < right.getPerfekt()) return 1;
            return 0;
        }
}
