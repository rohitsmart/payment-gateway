package com.payment.gateway.user;

import com.payment.gateway.entity.User;
import com.payment.gateway.security.UserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/protected/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "APIs related to user management and Client Creation")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private final UserService userService;

    private final UserDetail userDetail;

    @PostMapping("/create-client")
    @Operation(summary = "Create a new client")
    public ResponseEntity<UserResponse> createClient(@RequestBody UserRequest userRequest) {
        User user = userDetail.getUser();
      return ResponseEntity.ok(userService.createClient(userRequest,user));
    }

    @GetMapping("/client")
    @Operation(summary = "Fetch the client details for the authenticated user")
    public ResponseEntity<UserResponse> getClient() {
        User user = userDetail.getUser();
        return ResponseEntity.ok(userService.fetchClient(user));

    }

}
