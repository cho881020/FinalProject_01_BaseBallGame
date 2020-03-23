package kr.co.tjoeun.finalproject_01_baseballgame.data;

import java.io.Serializable;

public class Message implements Serializable {

    private String content;
    private String speaker; // 말한사람 내가? 컴퓨터?

    public Message(String content, String speaker) {
        this.content = content;
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
