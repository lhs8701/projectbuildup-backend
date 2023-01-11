package projectbuildup.saver.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.user.repository.MemberJpaRepository;
import projectbuildup.saver.domain.user.error.exception.CResourceNotFoundException;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String idToken) throws UsernameNotFoundException {
        return memberJpaRepository.findByIdToken(idToken).orElseThrow(CResourceNotFoundException::new);
    }
}