package kr.co.tjoeun.finalproject_01_baseballgame.data;

import java.io.Serializable;

public class Message  implements Serializable {

    private String content;
    private String speaker; // 말하는 사람, me or computor

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

    public Message(String content, String speaker) {
        this.content = content;
        this.speaker = speaker;


    }
}
