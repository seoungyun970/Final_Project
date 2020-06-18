package com.example.myapplication.resources;


import com.example.myapplication.R;
import com.example.myapplication.models.Hat;

import java.math.BigDecimal;

public class Hats {

    public static Hat[] getHats(){
        return SNAPBACKS;
    }

    public static final Hat SNAPBACK_BLACK = new Hat("Black Snapback", R.drawable.fragment1,
             new BigDecimal(20.99), 9377376);
    public static final Hat SNAPBACK_CAMO = new Hat("Camo Snapback", R.drawable.main,
             new BigDecimal(20.99), 9377377);
    public static final Hat SNAPBACK_GREY = new Hat("Grey Snapback", R.drawable.fragment3,
             new BigDecimal(20.99), 9377378);


    public static final Hat[] SNAPBACKS = {SNAPBACK_BLACK, SNAPBACK_CAMO, SNAPBACK_GREY};



}