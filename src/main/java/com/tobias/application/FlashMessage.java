package com.tobias.application;

import jakarta.servlet.http.HttpSession;

public class FlashMessage {
    public static void set(HttpSession session, String type, String message) {
        session.setAttribute("flashType", type);
        session.setAttribute("flashMessage", message);
    }

    public static void clear(HttpSession session) {
        session.removeAttribute("flashType");
        session.removeAttribute("flashMessage");
    }
}
