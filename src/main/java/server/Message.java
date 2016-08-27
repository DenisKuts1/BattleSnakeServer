package server;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by denak on 24.08.2016.
 */
public class Message implements Serializable {
    private Queue<Object> parcel = new LinkedList<>();

    public Message addUnit(Object unit) {
        parcel.add(unit);
        return this;
    }

    public Object getUnit() {
        return parcel.poll();
    }
}
