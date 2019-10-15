package com.itcast.travel.service;

public interface FavoriteService {
    Boolean isfavorite(String rid, int uid);

    void addfavorite(String rid, int uid);
}
