package com.sandbox.sandbox.tools;

import com.google.ar.sceneform.math.Vector3;

/**
 * Created by joe on 3/7/19.
 */

public class SoundObject {
    public Vector3 SoundPosition;
    public int playCount;
    public boolean playing;
    public int SoundIndex;
    public int duration;

    public SoundObject(Vector3 pos, int i){
        this.SoundPosition = pos;
        this.playCount = 0;
        this.playing = false;
        this.SoundIndex = i;
        this.duration = -1;
    }
}
