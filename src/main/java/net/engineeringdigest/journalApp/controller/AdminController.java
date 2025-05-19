package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.UserEntry;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppCache appCache;

    @Operation(summary = "Get all users")
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntry> all = userService.getAll();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Create Admin")
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody UserEntry user) {
        userService.saveNewAdmin(user);
    }

    @GetMapping("/clear-app-cache")
    @Operation(summary = "Clear Cache")
    public void clearAppCache() {
        appCache.init();
    }
}