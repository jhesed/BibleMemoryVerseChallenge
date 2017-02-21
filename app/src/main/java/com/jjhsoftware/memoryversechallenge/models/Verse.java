package com.jjhsoftware.memoryversechallenge.models;

/**
 * Created by Jhesed 2016/12/19
 */
public class Verse {
    public final int id;
    public final String name;
    public final String contentEnglish;
    public final String contentFilipino;

    public Verse(int id, String verse, String contentEnglish, String contentFilipino) {
        this.id = id;  // index in array
        this.name = verse;
        this.contentEnglish = contentEnglish;
        this.contentFilipino = contentFilipino;
    }
}
