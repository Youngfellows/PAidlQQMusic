package com.pandora.qqmusic;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    /**
     * 歌曲名
     */
    private String name;

    /**
     * 歌手
     */
    private String singer;

    /**
     * 歌曲封面图
     */
    private String cover;

    public Song() {

    }

    public Song(String name, String singer, String cover) {
        this.name = name;
        this.singer = singer;
        this.cover = cover;
    }

    protected Song(Parcel in) {
        name = in.readString();
        singer = in.readString();
        cover = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(singer);
        dest.writeString(cover);
    }

//    public void readFromParcel(Parcel source) {
//        this.name = source.readString();
//        this.singer = source.readString();
//        this.cover = source.readString();
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
