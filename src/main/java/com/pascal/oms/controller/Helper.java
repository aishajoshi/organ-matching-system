package com.pascal.oms.controller;

import com.pascal.oms.entities.Session;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;


public class Helper {
    public static Session getAuthSession(HttpSession httpSession) {
        Session session = (Session) httpSession.getAttribute("authSession");
        if (session == null) {
            session = new Session();
            setAuthSession(httpSession, session);
        }
        return session;
    }


    public static void setAuthSession(HttpSession httpSession, Session session) {
        if (session == null) {
            session = new Session();
        }
        httpSession.setAttribute("authSession", session);
    }

    public static void setAuthSession(Model model, Session session) {
        model.addAttribute("authSession", session);
    }
}
