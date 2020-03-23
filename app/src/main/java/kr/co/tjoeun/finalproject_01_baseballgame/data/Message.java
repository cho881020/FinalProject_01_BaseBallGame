package kr.co.tjoeun.finalproject_01_baseballgame.data;

import java.io.Serializable;

public class Message implements Serializable {

    private String content;
    private String speaker; //말한 사람 (나 OR 컴퓨터)

    public void setContent(String content) {
        this.content = content;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public String getSpeaker() {
        return speaker;
    }

    public Message(String content, String speaker) {
        this.content = content;
        this.speaker = speaker;
    }
}
