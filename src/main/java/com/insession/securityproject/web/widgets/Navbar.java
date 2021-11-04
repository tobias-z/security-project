package com.insession.securityproject.web.widgets;

import com.insession.securityproject.domain.user.UserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Navbar {

    private final HttpServletRequest request;

    public Navbar(HttpServletRequest request) {
        this.request = request;
    }

    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Home", "/", UserRole.USER, UserRole.NO_USER, UserRole.ADMIN));
        items.add(new Item("Login", "/login", UserRole.NO_USER));
        items.add(new Item("Admin", "/admin", UserRole.ADMIN));
        items.add(new Item("Profile", "/profile", UserRole.USER));
        items.add(new Item("Products", "/products", UserRole.USER, UserRole.NO_USER));
        return items;
    }

    public List<Item> getMenuItems() {
        UserRole role = (UserRole) request.getSession().getAttribute("role");
        List<Item> items = getItems();

        return items.stream()
                .filter(item -> Arrays.asList(item.getRoles()).contains(role))
                .collect(Collectors.toList());
    }

    public class Item {
        private final String name;
        private final String url;
        private final UserRole[] roles;

        Item(String name, String url, UserRole... roles) {
            this.name = name;
            this.url = url;
            this.roles = roles;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public UserRole[] getRoles() {
            return roles;
        }

        public boolean isActive() {
            return request.getRequestURI().endsWith(url);
        }
    }
}
