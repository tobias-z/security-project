package com.insession.securityproject.web.widgets;

import com.insession.securityproject.domain.user.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class Navbar {

    private final HttpServletRequest request;

    public Navbar(HttpServletRequest request) {
        this.request = request;
    }

    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Home", "/", UserRole.USER));
        items.add(new Item("Login", "/login", UserRole.USER));
        items.add(new Item("Admin", "/admin", UserRole.ADMIN));
        items.add(new Item("Profile", "/profile", UserRole.USER));
        return items;
    }

    public List<Item> getMenuItems() {
        UserRole role = (UserRole) request.getSession().getAttribute("role");
        List<Item> items = getItems();

        if (role != null && role.equals(UserRole.ADMIN)) {
            return items;
        }

        return items.stream()
                .filter(item -> item.getRole().equals(UserRole.USER))
                .collect(Collectors.toList());
    }

    public class Item {

        private final String name;
        private final String url;
        private final UserRole role;

        Item(String name, String url, UserRole role) {
            this.name = name;
            this.url = url;
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public UserRole getRole() {
            return role;
        }
    }
}
