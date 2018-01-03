package com.rftdevgroup.transporthub.ui.view;

public enum Views {
    HOME_VIEW("home"),
    LIST_TRANSPORT_VIEW("transportView");

    private final String viewName;

    private Views(final String name) {
        this.viewName = name;
    }

    @Override
    public String toString() {
        return viewName;
    }
}
