package com.playfieldsync.auth;

import com.playfieldsync.auth.dto.AuthResponse;
import com.playfieldsync.auth.dto.LoginRequest;
import com.playfieldsync.auth.dto.RegisterRequest;
import com.playfieldsync.auth.jwt.JwtService;
import com.playfieldsync.entities.user.*;
import com.playfieldsync.exceptions.ResourceAlreadyExistException;
import com.playfieldsync.repositories.user.UserAddressRepository;
import com.playfieldsync.repositories.user.UserContactInfoRepository;
import com.playfieldsync.repositories.user.UserPhoneNumberRepository;
import com.playfieldsync.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAddressRepository addressRepository;
    @Autowired
    private UserPhoneNumberRepository phoneNumberRepository;
    @Autowired
    private UserContactInfoRepository userContactInfoRepository;

    public Optional<AuthResponse> register(RegisterRequest request) {

        if(userContactInfoRepository.existsByEmail(request.getEmail())) throw new ResourceAlreadyExistException("email", request.getEmail());
        if(repository.existsByUsername(request.getUsername())) throw new ResourceAlreadyExistException("usuario", request.getUsername());
        if(userContactInfoRepository.existsByDni(request.getDni())) throw new ResourceAlreadyExistException("dni", request.getDni());
        User user = createUser(request);
        String token = jwtService.getToken(user);

        return Optional.of(AuthResponse.builder()
                .token(token)
                .role(user.getRole())
                .userId(user.getId())
                .username(user.getUsername())
                .build());

    }

    private User createUser(RegisterRequest request){
        UserAddress userAddress = createUserAddress(request);
        UserContactInfo contactInfo = createContactInfo(request, userAddress);
        User user = createUserEntity(request, contactInfo);
        return repository.save(user);
    }

    private User createUserEntity(RegisterRequest request, UserContactInfo contactInfo) {
        return repository.save(User
                .builder()
                .contactInfo(contactInfo)
                .role(ERole.USER)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .tickets(new HashSet<>())
                .build());
    }

    private UserContactInfo createContactInfo(RegisterRequest request, UserAddress userAddress) {
        Set<UserPhoneNumber> phoneNumbers = request.getPhoneNumbers().stream()
                .map(phoneNumber -> UserPhoneNumber.builder().phoneNumber(phoneNumber).build())
                .map(phoneNumberRepository::save)
                .collect(Collectors.toSet());

        return userContactInfoRepository.save(UserContactInfo
                .builder()
                .address(userAddress)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dni(request.getDni())
                .birthdate(request.getBirthdate())
                .phoneNumbers(phoneNumbers)
                .build());
    }

    private UserAddress createUserAddress(RegisterRequest request) {
        return addressRepository.save(UserAddress.builder()
                .country(request.getCountry())
                .province(request.getProvince())
                .city(request.getCity())
                .street(request.getStreet())
                .streetNumber(request.getNumberStreet())
                .postalCode(request.getPostalCode())
                .build());
    }

    public Optional<AuthResponse> login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return Optional.of(AuthResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .token(token)
                .build());

    }
}
