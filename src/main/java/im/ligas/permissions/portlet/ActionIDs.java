package im.ligas.permissions.portlet;

/**
 * Created by ligasm on 06/07/17.
 */
public enum ActionIDs {
    ADD_TO_PAGE("addToPage"),
    CONFIGURATION("configuration"),
    VIEW("view"),
    VIEW_EDIT("viewEdit"),
    DELETE("delete");

    private String permission;

    ActionIDs(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return permission;
    }
}
