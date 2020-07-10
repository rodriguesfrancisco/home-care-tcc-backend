package unip.tcc.homecare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import unip.tcc.homecare.dto.TokenDTO;

import java.util.stream.Collectors;

@RestController()
public class UserinfoController {

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUsername(userDetails.getUsername());
        tokenDTO.setRoles(userDetails.getAuthorities()
                            .stream()
                            .map(a -> ((GrantedAuthority) a).getAuthority())
                            .collect(Collectors.toList()));

        return ResponseEntity.ok(tokenDTO);
    }
}
