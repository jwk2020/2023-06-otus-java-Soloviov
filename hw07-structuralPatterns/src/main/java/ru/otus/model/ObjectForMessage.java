package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

import static java.util.List.copyOf;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage clone() {
        var copy = new ObjectForMessage();
        if (this.getData() != null) {
            copy.setData(List.copyOf(this.getData()));
        }
        return copy;
    }

    @Override
    public String toString() {
        return "ObjectForMessage{" +
                "data=" + data +
                '}';
    }
}
