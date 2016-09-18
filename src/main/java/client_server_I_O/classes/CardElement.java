package client_server_I_O.classes;

import java.io.Serializable;

public class CardElement implements Serializable {

    static final long serialVersionUID = 4566843898265039363l;

    public int role;

    public CardElement() {}

    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }

}