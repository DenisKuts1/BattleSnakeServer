package client_server_I_O.classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by denak on 26.08.2016.
 */
public enum Role implements Serializable {

    OWN_HEAD(1), OWN_BODY(2), OWN_TAIL(3), ENEMY_HEAD(4), ENEMY_BODY(5), ENEMY_TAIL(6), BARRIER(7), EMPTY(8),
    A_O_OWN_HEAD(9), A_O_OWN_BODY(10), A_O_OWN_TAIL(11), A_O_ENEMY_HEAD(12), A_O_ENEMY_BODY(13), A_O_ENEMY_TAIL(14), A_O_BARRIER(15), A_O_EMPTY(16),
    A_P_OWN_HEAD(17), A_P_OWN_BODY(18), A_P_OWN_TAIL(19), A_P_ENEMY_HEAD(20), A_P_ENEMY_BODY(21), A_P_ENEMY_TAIL(22), A_P_BARRIER(23), A_P_EMPTY(24),
    OR_OWN_HEAD(25), OR_OWN_BODY(26), OR_OWN_TAIL(27), OR_ENEMY_HEAD(28), OR_ENEMY_BODY(29), OR_ENEMY_TAIL(30), OR_BARRIER(31), OR_EMPTY(32),
    EXCEPT_OWN_HEAD(33), EXCEPT_OWN_BODY(34), EXCEPT_OWN_TAIL(35), EXCEPT_ENEMY_HEAD(36), EXCEPT_ENEMY_BODY(37), EXCEPT_ENEMY_TAIL(38), EXCEPT_BARRIER(39), EXCEPT_EMPTY(40);
    static final long serialVersionUID = 9213284845659098448L;
    public static ArrayList<Role> andOrangeRoles = new ArrayList<>();
    public static ArrayList<Role> andPinkRoles = new ArrayList<>();
    public static ArrayList<Role> orRoles = new ArrayList<>();
    public static ArrayList<Role> exceptRole = new ArrayList<>();

    public static Role role(int key){
        for(Role role : Role.values()) {
            if(role.key == key){
                return role;
            }
        }
        return null;
    }

    public int key;
    Role(int key){
        this.key = key;
    }

    public Role getRole() {
        switch (this) {
            case OWN_HEAD:
            case A_O_OWN_HEAD:
            case A_P_OWN_HEAD:
            case OR_OWN_HEAD:
            case EXCEPT_OWN_HEAD: {
                return OWN_HEAD;
            }

            case OWN_BODY:
            case A_O_OWN_BODY:
            case A_P_OWN_BODY:
            case OR_OWN_BODY:
            case EXCEPT_OWN_BODY: {
                return OWN_BODY;
            }

            case OWN_TAIL:
            case A_O_OWN_TAIL:
            case A_P_OWN_TAIL:
            case OR_OWN_TAIL:
            case EXCEPT_OWN_TAIL: {
                return OWN_TAIL;
            }

            case ENEMY_HEAD:
            case A_O_ENEMY_HEAD:
            case A_P_ENEMY_HEAD:
            case OR_ENEMY_HEAD:
            case EXCEPT_ENEMY_HEAD: {
                return ENEMY_HEAD;
            }

            case ENEMY_BODY:
            case A_O_ENEMY_BODY:
            case A_P_ENEMY_BODY:
            case OR_ENEMY_BODY:
            case EXCEPT_ENEMY_BODY: {
                return ENEMY_BODY;
            }
            case ENEMY_TAIL:
            case A_O_ENEMY_TAIL:
            case A_P_ENEMY_TAIL:
            case OR_ENEMY_TAIL:
            case EXCEPT_ENEMY_TAIL: {
                return ENEMY_TAIL;
            }
            case BARRIER:
            case A_O_BARRIER:
            case A_P_BARRIER:
            case OR_BARRIER:
            case EXCEPT_BARRIER: {
                return BARRIER;
            }
            default: {
                return EMPTY;
            }
        }
    }

    static {
        andOrangeRoles.add(A_O_OWN_HEAD);
        andOrangeRoles.add(A_O_OWN_BODY);
        andOrangeRoles.add(A_O_OWN_TAIL);
        andOrangeRoles.add(A_O_ENEMY_HEAD);
        andOrangeRoles.add(A_O_ENEMY_BODY);
        andOrangeRoles.add(A_O_ENEMY_TAIL);
        andOrangeRoles.add(A_O_BARRIER);
        andOrangeRoles.add(A_O_EMPTY);

        andPinkRoles.add(A_P_OWN_HEAD);
        andPinkRoles.add(A_P_OWN_BODY);
        andPinkRoles.add(A_P_OWN_TAIL);
        andPinkRoles.add(A_P_BARRIER);
        andPinkRoles.add(A_P_EMPTY);
        andPinkRoles.add(A_P_ENEMY_HEAD);
        andPinkRoles.add(A_P_ENEMY_BODY);
        andPinkRoles.add(A_P_ENEMY_TAIL);

        orRoles.add(OR_OWN_HEAD);
        orRoles.add(OR_OWN_BODY);
        orRoles.add(OR_OWN_TAIL);
        orRoles.add(OR_ENEMY_HEAD);
        orRoles.add(OR_ENEMY_BODY);
        orRoles.add(OR_ENEMY_TAIL);
        orRoles.add(OR_BARRIER);
        orRoles.add(OR_EMPTY);

        exceptRole.add(EXCEPT_OWN_HEAD);
        exceptRole.add(EXCEPT_OWN_BODY);
        exceptRole.add(EXCEPT_OWN_TAIL);
        exceptRole.add(EXCEPT_ENEMY_HEAD);
        exceptRole.add(EXCEPT_ENEMY_BODY);
        exceptRole.add(EXCEPT_ENEMY_TAIL);
        exceptRole.add(EXCEPT_BARRIER);
        exceptRole.add(EXCEPT_EMPTY);
    }

}
