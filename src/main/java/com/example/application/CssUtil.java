package com.example.application;

import com.vaadin.flow.server.VaadinSession;

public class CssUtil {
    public static void setCustomCss(String css) {
        VaadinSession.getCurrent().setAttribute("customCss", css);
    }

    public static String getCustomCss() {
        return (String) VaadinSession.getCurrent().getAttribute("customCss");
    }
}
