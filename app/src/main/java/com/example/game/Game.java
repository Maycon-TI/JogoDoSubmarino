package com.example.game;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {
    private String dificuldade = "Normal";
    private int bombas = 4;
    private int gastoDeOxigenio = 3;

    protected Game(Parcel in) {
        dificuldade = in.readString();
        bombas = in.readInt();
        gastoDeOxigenio = in.readInt();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public Game() {

    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }

    public int getBombas() {
        return bombas;
    }

    public void setBombas(int bombas) {
        this.bombas = bombas;
    }

    public int getGastoDeOxigenio() {
        return gastoDeOxigenio;
    }

    public void setGastoDeOxigenio(int gastoDeOxigenio) {
        this.gastoDeOxigenio = gastoDeOxigenio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dificuldade);
        parcel.writeInt(bombas);
        parcel.writeInt(gastoDeOxigenio);
    }
}
