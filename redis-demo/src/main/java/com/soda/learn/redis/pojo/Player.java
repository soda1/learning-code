package com.soda.learn.redis.pojo;

/**
 * 运动员
 */
public class Player {

    private String name;

    private int distance;


    public Player() {

    }


    public Player(String name, int distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public int getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                '}';
    }
}
