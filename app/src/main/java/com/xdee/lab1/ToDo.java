package com.xdee.lab1;

import java.util.HashMap;

public class ToDo {
    String id,title,content;

    public ToDo(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    HashMap<String,Object> convertToHashMap(){
        HashMap<String,Object> work = new HashMap<>();
        work.put("id",id);
        work.put("title",title);
        work.put("content",content);
        return work;
    }
    public ToDo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
