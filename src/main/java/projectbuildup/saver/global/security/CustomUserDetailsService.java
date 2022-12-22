package projectbuildup.saver.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return userJpaRepository.findByIdToken(loginId).orElseThrow(CUserNotFoundException::new);
    }
}