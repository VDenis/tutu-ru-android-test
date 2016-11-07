package com.app.denis.tutuapp.model;

import java.util.List;

/**
 * Created by Denis on 05.11.2016.
 */

public class Journey {
    //массив пунктов отправления
    private List<City> citiesFrom;
    //private City[] citiesFrom;
    //массив пунктов назначения
    private List<City> citiesTo;
    //private City[] citiesTo;

    public List<City> getCitiesTo() {
        return citiesTo;
    }

    public void setCitiesTo(List<City> citiesTo) {
        this.citiesTo = citiesTo;
    }

    public List<City> getCitiesFrom() {
        return citiesFrom;
    }

    public void setCitiesFrom(List<City> citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

/*    public City[] getCitiesFrom() {
        return citiesFrom;
    }

    public void setCitiesFrom(City[] citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

    public City[] getCitiesTo() {
        return citiesTo;
    }

    public void setCitiesTo(City[] citiesTo) {
        this.citiesTo = citiesTo;
    }*/
}
