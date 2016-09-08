package client_server_I_O.classes;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by denak on 24.08.2016.
 */
public class Message implements Serializable {
    private Queue<Object> parcel = new LinkedList<>();

    public Message() {}

    public Queue<Object> getParcel() {
        return parcel;
    }

    public void setParcel(Queue<Object> parcel) {
        this.parcel = parcel;
    }

    public Message(Object ... units){
        for(int i = 0; i < units.length; i++){
            parcel.add(units[i]);
        }
    }

    public void addUnit(Object ... units) {
        for(int i = 0; i < units.length; i++){
            parcel.add(units[i]);
        }
    }

    public Object getUnit() {
        return parcel.poll();
    }
}
