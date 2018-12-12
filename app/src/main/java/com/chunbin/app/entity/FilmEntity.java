package com.chunbin.app.entity;

import java.util.List;

public class FilmEntity {
    private List<Trailers> trailers;
    public void setTrailers(List<Trailers> trailers) {
        this.trailers = trailers;
    }
    public List<Trailers> getTrailers() {
        return trailers;
    }
}
