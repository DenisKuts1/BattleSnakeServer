package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by denak on 26.08.2016.
 */
public enum Role implements Serializable {
    OWN_HEAD, OWN_BODY, OWN_TAIL, ENEMY_HEAD, ENEMY_BODY, ENEMY_TAIL, BARRIER, EMPTY,
    A_O_OWN_HEAD, A_O_OWN_BODY, A_O_OWN_TAIL, A_O_ENEMY_HEAD, A_O_ENEMY_BODY, A_O_ENEMY_TAIL, A_O_BARRIER, A_O_EMPTY,
    A_P_OWN_HEAD, A_P_OWN_BODY, A_P_OWN_TAIL, A_P_ENEMY_HEAD, A_P_ENEMY_BODY, A_P_ENEMY_TAIL, A_P_BARRIER, A_P_EMPTY,
    OR_OWN_HEAD, OR_OWN_BODY, OR_OWN_TAIL, OR_ENEMY_HEAD, OR_ENEMY_BODY, OR_ENEMY_TAIL, OR_BARRIER, OR_EMPTY,
    EXCEPT_OWN_HEAD, EXCEPT_OWN_BODY, EXCEPT_OWN_TAIL, EXCEPT_ENEMY_HEAD, EXCEPT_ENEMY_BODY, EXCEPT_ENEMY_TAIL, EXCEPT_BARRIER, EXCEPT_EMPTY;

    public static ArrayList<Role> andOrangeRoles = new ArrayList<>();
    public static ArrayList<Role> andPinkRoles = new ArrayList<>();
    public static ArrayList<Role> orRoles = new ArrayList<>();
    public static ArrayList<Role> exceptRole = new ArrayList<>();

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
