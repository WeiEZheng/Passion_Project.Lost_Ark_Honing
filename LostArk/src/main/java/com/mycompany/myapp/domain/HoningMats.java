package com.mycompany.myapp.domain;

public final class HoningMats {
    private final static HoningMats honingMats = new HoningMats();

    public static HoningMats getHoningMatsInstance(){
        return honingMats;
    }
}
