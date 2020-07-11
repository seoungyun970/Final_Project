package com.example.myapplication.Adapter;

import java.util.ArrayList;

public class SampleData {

    ArrayList<Store> items = new ArrayList<>();

    public ArrayList<Store> getItems() {

        Store store1 = new Store("https://modo-phinf.pstatic.net/20170919_70/1505799761762pd6lb_PNG/mosa3OsZOY.png?type=w1100",
                "분식", "엽기떡볶이", "대구시 남구");

        Store store2 = new Store("https://lh3.googleusercontent.com/proxy/AdD4-00W2UiVNMf1xdGMr0oIR_6XgjvJuzuFMx5aNU0C_MuJRSXfy8zjhL63KVdCFHJovfHc_RGn4njlzOFKFIj_-wExYJnO73w07VA3RU_5O-3br0Hf8MAn9cKibb8KbjU_feVd1LSfX6go_hnyKxqEAMOCKu2OWdcy3jvdhPFKwdiakVoCUjulfZ2pX1Zzg1Y7XxW_AdXq7oRTwColg8x6co2YsVI91cWkDCzbAxHz0AEfor-yOH57PRwC3QSqBF31Wj5wPlemecVLNAH5I8i6aIi12u9jrUEIdTzFoAg",
                "양식", "진짜파스타", "서울시 마포구");

        Store store3 = new Store("https://search.pstatic.net/common/?autoRotate=true&type=w560_sharpen&src=http%3A%2F%2Fldb.phinf.naver.net%2F20180403_288%2F1522751067539KtHpK_PNG%2FTimENC9Zp9xN1uSM1_Tr-2TR.png",
                "중식", "홍콩반점", "대구시 북구");

        Store store4 = new Store("https://lh3.googleusercontent.com/proxy/eFQtgNcTjYX2OOu0HhB_Cf6tJISLwQO_tQQ-YAll3GqqS4BIDJ1itIHLG9mcjzVNw1vO5xnoJxnuRRuKQFFkuo-1u1tlnSFQrTPwC8rhm5FAtc_Z8VGikZgNxrdz",
                "양식", "교촌치킨", "울산시 중구");

        Store store5 = new Store("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQZUD-ZcavUUJNpP2mFMn6TGhQqj-5dG3q9twObrlfXz8QWN2Gn&usqp=CAU",
                "한식", "본죽", "대구시 중구");

        Store store6 = new Store("https://lh3.googleusercontent.com/proxy/NP2TO5SkZxoGf2mM_tlfmRqDqW4W9jBoqrRg6zLaldG9Z7hfLrKX3mtbO65DWVzVouPPeaA1aKxGG8YUADIaD6qxhDx3niGWZwIRDoyAvAbH3-TjvbJL6WDLM9o_Hft9Wvhb5GQWradaDlvD49zyuthx6lhotwvM7dzZ8uUNIOtzonjYhSgJvKqE0zq-K0zhnznwHtFhpJKqJMYuTwuYcvE19bBa0oMQbrHacGMjQirD9DqfapdjVTG-miuGVZozPh7_f3pxF3tkZtpFcXRbkh3yEnY9zPExuXAHrlcfYAIu01PVl0scYVy_wjPJgNtKrEEp1z1QTsENLdh1rTeJhUydVCUzyfWgiMjmXAXOZSye9fnX89vskWpgRITvJnS_PQ",
                "일식", "미소야", "대구시 달서구");

        items.add(store1);
        items.add(store2);
        items.add(store3);

        items.add(store4);
        items.add(store5);
        items.add(store6);


        return items;
    }
}