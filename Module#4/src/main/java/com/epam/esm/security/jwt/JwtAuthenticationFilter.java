//package com.epam.esm.security.jwt;
//
//import com.epam.esm.security.CustomUserDetails;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private static final String HEADER_STRING = "Authorization";
//    private static final String TOKEN_PREFIX = "Bearer ";
//
//    @Autowired
//    private JwtProvider jwtProvider;
//
//    @Value("${jwt.sign-in.uri}")
//    private String jwtSignInUri;
//
//    @Value("${jwt.sign-up.uri}")
//    private String jwtSignUpUri;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @PostConstruct
//    private void init() {
//        setFilterProcessesUrl(jwtSignInUri);
//        setFilterProcessesUrl(jwtSignUpUri);
//    }
//
//    @Override
//    @SneakyThrows
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        CustomUserDetails creds = objectMapper
//                .readValue(request.getInputStream(), CustomUserDetails.class);
//
//        return this.getAuthenticationManager().authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        creds.getUsername(),
//                        creds.getPassword())
//        );
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        String token = jwtProvider.createToken(((CustomUserDetails) authResult.getPrincipal()).getUsername());
//        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
//    }
//}
