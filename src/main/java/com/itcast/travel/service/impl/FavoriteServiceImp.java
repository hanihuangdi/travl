package com.itcast.travel.service.impl;

import com.itcast.travel.dao.FavoriteDao;
import com.itcast.travel.dao.impl.FavoriteDaoImp;
import com.itcast.travel.domain.Favorite;
import com.itcast.travel.service.FavoriteService;

public class FavoriteServiceImp implements FavoriteService {
    FavoriteDao favoriteDao =  new FavoriteDaoImp();

    @Override
    public Boolean isfavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findOne(Integer.parseInt(rid),uid);
        if(favorite==null){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void addfavorite(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
