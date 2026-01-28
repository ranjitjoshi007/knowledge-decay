package ie.ranjitjoshi.knowledgedecay.controller;

import ie.ranjitjoshi.knowledgedecay.domain.entity.UserAccount;
import ie.ranjitjoshi.knowledgedecay.domain.enums.Role;
import ie.ranjitjoshi.knowledgedecay.repository.UserAccountRepository;
import ie.ranjitjoshi.knowledgedecay.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserAccountRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public void register(@RequestBody UserAccount user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER); // default role
        userRepo.save(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserAccount request) {
        System.out.println("Login attempt for: " + request.getEmail());
        UserAccount user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());
        return Map.of("token", token);
    }


}
