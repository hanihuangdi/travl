package com.itcast.travel.dao;

import com.itcast.travel.domain.Seller;

public interface SellerDao {
    Seller findSeller(int rid);
}
