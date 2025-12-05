package org.devoir.restaurantservice.service.impl;

import org.devoir.restaurantservice.dto.RestaurantDTO;
import org.devoir.restaurantservice.exception.ResourceNotFoundException;
import org.devoir.restaurantservice.model.Restaurant;
import org.devoir.restaurantservice.repository.RestaurantRepository;
import org.devoir.restaurantservice.service.RestaurantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return convertToDTO(restaurant);
    }

    @Override
    @Transactional
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = convertToEntity(restaurantDTO);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToDTO(savedRestaurant);
    }

    @Override
    @Transactional
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        
        // Mise à jour des champs
        existingRestaurant.setName(restaurantDTO.getName());
        existingRestaurant.setAddress(restaurantDTO.getAddress());
        existingRestaurant.setPhone(restaurantDTO.getPhone());
        existingRestaurant.setEmail(restaurantDTO.getEmail());
        existingRestaurant.setCuisineType(restaurantDTO.getCuisineType());
        existingRestaurant.setRating(restaurantDTO.getRating());
        existingRestaurant.setIsOpen(restaurantDTO.getIsOpen());
        existingRestaurant.setIsActive(restaurantDTO.getIsActive());
        existingRestaurant.setDeliveryFee(restaurantDTO.getDeliveryFee());
        existingRestaurant.setMinimumOrderAmount(restaurantDTO.getMinimumOrderAmount());
        
        return convertToDTO(restaurantRepository.save(existingRestaurant));
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantDTO> getAvailableRestaurants() {
        return restaurantRepository.findByIsOpenTrueAndIsActiveTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RestaurantDTO convertToDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    private Restaurant convertToEntity(RestaurantDTO restaurantDTO) {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }
}
