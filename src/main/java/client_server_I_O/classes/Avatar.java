package client_server_I_O.classes;


import java.io.Serializable;

public class Avatar implements Serializable {

    byte[] imageBytes;

    public Avatar() {}

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] image) {
        this.imageBytes = image;
    }
}
