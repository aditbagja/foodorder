package com.orderapp.foodorder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.request.AddToCartRequestDTO;
import com.orderapp.foodorder.dto.response.MessageResponse;
import com.orderapp.foodorder.exception.classes.DataNotFoundException;
import com.orderapp.foodorder.model.mongoDb.CartMongo;
import com.orderapp.foodorder.model.mongoDb.MenuMongo;
import com.orderapp.foodorder.model.mongoDb.RestaurantMongo;
import com.orderapp.foodorder.model.mongoDb.UsersMongo;
import com.orderapp.foodorder.model.mongoDb.CartMongo.MenusCartInfo;
import com.orderapp.foodorder.repository.CartMongoRespository;
import com.orderapp.foodorder.repository.MenuMongoRepository;
import com.orderapp.foodorder.repository.RestaurantMongoRepository;
import com.orderapp.foodorder.repository.UsersMongoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService {
    @Autowired
    private CartMongoRespository cartMongoRespository;

    @Autowired
    private UsersMongoRepository usersMongoRepository;

    @Autowired
    private MenuMongoRepository menuMongoRepository;

    @Autowired
    private RestaurantMongoRepository restaurantMongoRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final HttpStatus statusOk = HttpStatus.OK;

    public ResponseEntity<Object> addToCart(AddToCartRequestDTO request) {
        UsersMongo users = usersMongoRepository.findById(request.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Data User tidak ditemukan"));
        RestaurantMongo resto = restaurantMongoRepository.findById(request.getRestoId())
                .orElseThrow(() -> new DataNotFoundException("Data Resto tidak ditemukan"));
        MenuMongo menu = menuMongoRepository.findById(request.getMenuId())
                .orElseThrow(() -> new DataNotFoundException("Data Menu tidak ditemukan"));
        Optional<CartMongo> cartExist = cartMongoRespository.findByUserAndResto(users, resto);

        MenusCartInfo newMenus = MenusCartInfo.builder()
                .id(menu.getId())
                .menuName(menu.getName())
                .rating(menu.getRating())
                .level(menu.getLevel())
                .harga(menu.getHarga())
                .quantity(1)
                .build();

        if (cartExist.isPresent()) {
            boolean isMenuExist = cartExist.get().getMenus().stream()
                    .anyMatch(data -> data.getId().equals(menu.getId()));

            if (isMenuExist) {
                MenusCartInfo existingMenu = cartExist.get().getMenus().stream()
                        .filter(data -> data.getId().equals(menu.getId())).findFirst().get();
                existingMenu.setQuantity(existingMenu.getQuantity() + 1);
            } else {
                cartExist.get().getMenus().add(newMenus);
            }

            cartExist.get().setTotalHarga(
                    cartExist.get().getMenus().stream().mapToInt(m -> m.getHarga() * m.getQuantity()).sum());

            cartMongoRespository.save(cartExist.get());
        } else {
            List<MenusCartInfo> newListMenu = new ArrayList<>();
            newListMenu.add(newMenus);

            CartMongo newCart = CartMongo.builder()
                    .id(sequenceGeneratorService.generateSequence(CartMongo.SEQUENCE_NAME))
                    .user(users)
                    .resto(resto)
                    .menus(newListMenu)
                    .totalHarga(newMenus.getHarga())
                    .build();

            cartMongoRespository.save(newCart);
        }
        String message = "Menu " + menu.getName() + " berhasil ditambahkan ke keranjang";

        MessageResponse response = MessageResponse.builder()
                .message(message)
                .code(statusOk.value())
                .status(statusOk.getReasonPhrase())
                .build();

        log.info(message);

        return new ResponseEntity<>(response, statusOk);
    }
}
