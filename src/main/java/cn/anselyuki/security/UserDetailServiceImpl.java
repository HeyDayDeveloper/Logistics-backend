package cn.anselyuki.security;

import cn.anselyuki.repository.UserRepository;
import cn.anselyuki.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        Integer status = user.getStatus();
        Set<String> authorities = new HashSet<>();

        for (int i = 0; i <= status; i++) {
            authorities.add(Integer.toString(i));
        }
        //数据封装成UserDetails返回
        return new LoginUser(user, authorities);
    }
}
