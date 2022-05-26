package com.igit.icare;

public class value {

    Long value;
    String time;

public value(){

}
public value(Long value,String time){
    this.value=value;
    this.time=time;

}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}