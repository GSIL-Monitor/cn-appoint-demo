package com.jd.appoint.vo.page;

/**
 * Created by shaohongsen on 2018/5/19.
 */
public enum Direction {
    DESC("降序"), ASC("升序");
    private String description;

    Direction(String description) {
        this.description = description;
    }
}
