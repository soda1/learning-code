package com.soda.autoconfigure;


public class HelloService {

    private String name;

    private String say;

    public HelloService(String name, String say) {
        this.name = name;
        this.say = say;
    }

    public void sayHello() {
        System.out.println(this.name + " say: " + this.say);
    }

    public void eatApple() {
        System.out.println(this.name + "eating apple");
    }

    public String playGame(String gameName) {
        return this.name + " play " + gameName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }
}
