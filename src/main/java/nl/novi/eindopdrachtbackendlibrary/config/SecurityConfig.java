package nl.novi.eindopdrachtbackendlibrary.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${spring.security.oauth2.resourceserver.jwt.audiences}")
    private String audience;
    @Value("${client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(hp -> hp.disable())
                .csrf(csrf->csrf.disable())
                .cors(cors->{})
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                .decoder(jwtDecoder())
                        ))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/books", "/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authors", "/authors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/genres", "/genres/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/collections", "/collections/**").hasAnyRole("MEMBER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/collections", "/collections/*/books").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/collections/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/collections/*/books").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/collections/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/authors").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/authors/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/authors/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/genres").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/genres/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/books").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/books/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/books/*/authors/*").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/books/*/authors/*").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/loan-activities/user/*").hasAnyRole("MEMBER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/loan-activities").hasAnyRole("MEMBER", "EMPLOYEE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/loan-activities/*/return").hasAnyRole("MEMBER", "EMPLOYEE", "ADMIN")
                        .requestMatchers("/loan-activities/**").hasAnyRole("EMPLOYEE", "ADMIN")

                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

    }

    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

        OAuth2TokenValidator<Jwt> audienceValidator = new JwtAudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;

    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new Converter<>() {
            @Override
            public Collection<GrantedAuthority> convert(Jwt source) {
                Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                for (String authority : getAuthorities(source)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority( authority));
                }
                return grantedAuthorities;
            }
            private List<String> getAuthorities(Jwt jwt){
                Map<String, Object> resourceAcces = jwt.getClaim("resource_access");
                if(resourceAcces != null){
                    if( resourceAcces.get(clientId) instanceof Map) {
                        Map<String, Object> client = (Map<String, Object>) resourceAcces.get(clientId);
                        if(client != null & client.containsKey("roles")) {
                            return (List<String>) client.get("roles");
                        }
                    }
                }
                return new ArrayList<>();
            }
        });
        return jwtAuthenticationConverter;

    }
}
