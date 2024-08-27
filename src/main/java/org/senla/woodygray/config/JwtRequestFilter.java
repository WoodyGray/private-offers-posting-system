package org.senla.woodygray.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.woodygray.util.JwtTokenUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter {
    //extends OncePerRequestFilter
//    private final JwtTokenUtils jwtTokenUtils;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String userPhoneNumber = null;
//        String jwt = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")){
//            jwt = authHeader.substring(7);
//            try {
//                userPhoneNumber = jwtTokenUtils.getUserPhoneNumber(jwt);
//            } catch (ExpiredJwtException e){
//                System.out.println("Lifetime is out");
//            } catch (SignatureException e){
//                System.out.println("signature is not correct");
//            }
//        }
//
//        if (userPhoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    userPhoneNumber,
//                    null,
//                    jwtTokenUtils.getUserRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
//            );
//            SecurityContextHolder.getContext().setAuthentication(token);
//        }
//        filterChain.doFilter(request, response);
//    }
}
