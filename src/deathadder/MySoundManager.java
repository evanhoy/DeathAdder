/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathadder;

import audio.Playlist;
import audio.SoundManager;
import audio.Source;
import audio.Track;
import java.util.ArrayList;

/**
 *
 * @author evanhoy
 */
public class MySoundManager extends SoundManager {

    public static MySoundManager getSoundManager() {
        ArrayList<Track> tracks = new ArrayList<>();
        tracks.add(new Track(MySoundManager.DANKSTORM, Source.RESOURCE, "/deathadder/Dankstorm.mp3"));
        tracks.add(new Track(MySoundManager.DANKSTORM, Source.RESOURCE, "/deathadder/Speed.wav"));
        tracks.add(new Track(MySoundManager.DANKSTORM, Source.RESOURCE, "/deathadder/health.wav"));

        Playlist playlist = new Playlist(tracks);
        return new MySoundManager(playlist);
    }

    public MySoundManager(Playlist playlist) {
        super(playlist);
    }

    public static final String HEALTH = "DRUGS";
    public static final String SPEED = "USAIN";
    public static final String DANKSTORM = "DARUDE";

}
