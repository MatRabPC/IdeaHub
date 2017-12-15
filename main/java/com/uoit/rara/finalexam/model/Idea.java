package com.uoit.rara.finalexam.model;

public class Idea {

    private String name;
    private String desc;
    private String topic;
    private String sound;
    private String loc;

    public Idea(    String name,
                   String desc,
                   String topic,
                    String sound,
                    String loc)
    {
        setName(name);
        setDesc(desc);
        setTopic(topic);
        setSound(sound);
        setLoc(loc);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }



}

