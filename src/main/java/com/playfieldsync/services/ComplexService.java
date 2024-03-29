package com.playfieldsync.services;

import com.playfieldsync.dto.requests.ComplexRequest;
import com.playfieldsync.entities.complex.Complex;
import com.playfieldsync.entities.complex.ComplexAddress;
import com.playfieldsync.entities.complex.ComplexContactInfo;
import com.playfieldsync.entities.complex.ComplexPhoneNumber;
import com.playfieldsync.exceptions.ResourceAlreadyExistException;
import com.playfieldsync.repositories.complex.ComplexAddressRepository;
import com.playfieldsync.repositories.complex.ComplexContactInfoRepository;
import com.playfieldsync.repositories.complex.ComplexPhoneNumberRepository;
import com.playfieldsync.repositories.complex.ComplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComplexService {
    
    @Autowired
    private ComplexRepository complexRepository;
    @Autowired
    private ComplexContactInfoRepository contactInfoRepository;
    @Autowired
    private ComplexAddressRepository addressRepository;
    @Autowired
    private ComplexPhoneNumberRepository phoneNumberRepository;

    // =============================== POST ===============================
    /*
    Crea y retorna nuevo complejo.
    */
    public Optional<Complex> create(ComplexRequest request) {
        /*Verifica si el nombre o correo electrónico ya está en uso.*/
        if(complexRepository.existsByName(request.getName())) throw new ResourceAlreadyExistException("nombre", request.getName());
        if(contactInfoRepository.existsByEmail(request.getEmail())) throw new ResourceAlreadyExistException("email", request.getEmail());
        try {
            ComplexAddress complexAddress = createComplexAddress(request);
            ComplexContactInfo contactInfo = createContactInfo(request, complexAddress);
            Complex complex = createComplexEntity(request, contactInfo);
            return Optional.of(complex);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty(); // Manejar el caso en el que ocurra algún error
        }
    }

    private Complex createComplexEntity(ComplexRequest request, ComplexContactInfo contactInfo){
        return complexRepository.save(Complex
                .builder()
                .name(request.getName())
                .contactInfo(contactInfo)
                .fields(new HashSet<>())
                .build());
    }

    private ComplexContactInfo createContactInfo(ComplexRequest request, ComplexAddress address){

        ComplexContactInfo contactInfo = contactInfoRepository.save(ComplexContactInfo.builder()
                .email(request.getEmail())
                .address(addressRepository.save(address))
                .phoneNumbers(new HashSet<>())
                .build());

        for(String phone : request.getPhoneNumbers()){
            contactInfo.getPhoneNumbers().add(
                    phoneNumberRepository.save(
                            ComplexPhoneNumber.builder()
                                    .number(phone)
                                    .contactInfo(contactInfo)
                                    .build()
                    )
            );
        }
        return contactInfoRepository.save(contactInfo);
    }

    private ComplexAddress createComplexAddress(ComplexRequest request){
        return addressRepository.save(ComplexAddress.builder()
                .country(request.getCountry())
                .province(request.getProvince())
                .city(request.getCity())
                .street(request.getStreet())
                .streetNumber(request.getStreetNumber())
                .postalCode(request.getPostalCode())
                .build());
    }

    // =============================== GET ===============================

    /*Busca y retorna una lista con todos los complejos*/
    public List<Complex> findAll(){
        List<Complex> complexList = this.complexRepository.findAll();
        return complexList;
    }

    /*Busca y retorna un complejo por id*/
    public Optional<Complex> findById(Long id) {
        return this.complexRepository.findById(id);
    }

    // =============================== DELETE ===============================
    public void deleteComplex(Long id){
        this.complexRepository.deleteById(id);
    }

    // =============================== PUT ===============================
    /*Actualiza un complejo en base a su id */
    public Optional<Complex> updateById(Long id, ComplexRequest request){
        Optional<Complex> optComplex = this.complexRepository.findById(id);
        if(optComplex.isEmpty()) return Optional.empty();

        Complex complex = optComplex.get();
        complex.setName(request.getName());
        complex.getContactInfo().setEmail(request.getEmail());
        complex.getContactInfo().getAddress().setCountry(request.getCountry());
        complex.getContactInfo().getAddress().setProvince(request.getProvince());
        complex.getContactInfo().getAddress().setCity(request.getCity());
        complex.getContactInfo().getAddress().setStreetNumber(request.getStreetNumber());
        complex.getContactInfo().getAddress().setStreet(request.getStreet());
        complex.getContactInfo().getAddress().setPostalCode(request.getPostalCode());


        List<String> newPhoneNumbers = request.getPhoneNumbers();
        Set<ComplexPhoneNumber> existingPhoneNumbers = complex.getContactInfo().getPhoneNumbers();
        Iterator<ComplexPhoneNumber> phoneNumberIterator = existingPhoneNumbers.iterator();
        Iterator<String > newPhoneNumberIterator = newPhoneNumbers.iterator();
        while (phoneNumberIterator.hasNext() && newPhoneNumberIterator.hasNext()) {
            ComplexPhoneNumber phoneNumber = phoneNumberIterator.next();
            String newPhoneNumber = newPhoneNumberIterator.next();
            phoneNumber.setNumber(newPhoneNumber);
        }
        return Optional.of(this.complexRepository.save(complex));
    }


}
