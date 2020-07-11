package com.example.myapplication.VO;

import java.io.Serializable;

public class ReviewCountVO implements Serializable {
    int child_count;
    int supporter_count;
    int star_avg;

    public int getChild_count() {
        return child_count;
    }

    public void setChild_count(int child_count) {
        this.child_count = child_count;
    }

    public int getSupporter_count() {
        return supporter_count;
    }

    public void setSupporter_count(int supporter_count) {
        this.supporter_count = supporter_count;
    }

    public int getStar_avg() {
        return star_avg;
    }

    public void setStar_avg(int star_avg) {
        this.star_avg = star_avg;
    }

    @Override
    public String toString() {
        return "ReviewCountVO{" +
                "child_count=" + child_count +
                ", supporter_count=" + supporter_count +
                ", star_avg=" + star_avg +
                '}';
    }
}
