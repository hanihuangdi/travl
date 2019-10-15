package com.itcast.travel.dao;

import com.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    Favorite findOne(int parseInt, int uid);

    int findCount(int rid);

    void add(int parseInt, int uid);
}
