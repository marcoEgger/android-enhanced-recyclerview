package de.marco_egger.enhancedrecyclerview.model;

import android.support.annotation.NonNull;

/**
 * @author Marco Egger
 */
public class Task {

    @NonNull
    private final String name;

    public Task(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
