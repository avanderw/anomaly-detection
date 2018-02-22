package net.avdw.economy.api;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Container<T> {
    private final List<Stamp> stamps;
    private T good;

    public Container() {
        stamps = new LinkedList();
    }

    public Container(Container that) {
        stamps = new LinkedList();
        this.stamps.addAll(that.stamps);
    }

    public void pack(T good)
    {
        this.good = good;
    }

    public T unpack()
    {
        return good;
    }

    void stamp(String simpleName)
    {
        stamps.add(new Stamp(new Date(), simpleName));
    }

    @Override
    public String toString() {
        return stamps.toString();
    }
}
