package com.jacamars.dsp.rtb.shared;

/**
 * Created by ben on 10/4/17.
 */
public interface SharedObjectIF<V> {
    void handleMessage(V obj);
}
