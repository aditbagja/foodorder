package com.orderapp.foodorder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.request.CartRequestDTO;
import com.orderapp.foodorder.dto.response.CartListResponse;
import com.orderapp.foodorder.dto.response.CartListResponse.MenusCartInfoDTO;
import com.orderapp.foodorder.dto.response.MenuListResponse.RestoInfo;
import com.orderapp.foodorder.dto.response.OrderHistoricalResponse.CustomerInfo;
import com.orderapp.foodorder.dto.response.MessageResponse;
import com.orderapp.foodorder.dto.response.ResponseBodyDTO;
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

import jakarta.transaction.Transactional;
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

        @Transactional
        public ResponseEntity<Object> addToCart(CartRequestDTO request) {
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
                                existingMenu.setHarga(menu.getHarga() * existingMenu.getQuantity());
                        } else {
                                cartExist.get().getMenus().add(newMenus);
                        }

                        cartExist.get().setTotalHarga(
                                        cartExist.get().getMenus().stream()
                                                        .mapToInt(m -> m.getHarga()).sum());

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

        public ResponseEntity<Object> removeMenuFromCart(CartRequestDTO request) {
                UsersMongo users = usersMongoRepository.findById(request.getUserId())
                                .orElseThrow(() -> new DataNotFoundException("Data User tidak ditemukan"));
                RestaurantMongo resto = restaurantMongoRepository.findById(request.getRestoId())
                                .orElseThrow(() -> new DataNotFoundException("Data Resto tidak ditemukan"));
                MenuMongo menu = menuMongoRepository.findById(request.getMenuId())
                                .orElseThrow(() -> new DataNotFoundException("Data Menu tidak ditemukan"));
                CartMongo cartExist = cartMongoRespository.findByUserAndResto(users, resto)
                                .orElseThrow(() -> new DataNotFoundException("Data Cart tidak ditemukan"));

                List<MenusCartInfo> menuList = cartExist.getMenus();
                menuList = menuList.stream().filter(m -> !m.getId().equals(request.getMenuId()))
                                .toList();

                cartExist.setMenus(menuList);
                cartExist.setTotalHarga(
                                cartExist.getMenus().stream()
                                                .mapToInt(m -> m.getHarga() * m.getQuantity()).sum());
                cartMongoRespository.save(cartExist);

                if (menuList.size() == 0) {
                        cartMongoRespository.delete(cartExist);
                }

                String message = "Menu " + menu.getName() + " berhasil dihapus dari keranjang";
                MessageResponse response = MessageResponse.builder()
                                .message(message)
                                .code(statusOk.value())
                                .status(statusOk.getReasonPhrase())
                                .build();

                log.info(message);

                return new ResponseEntity<>(response, statusOk);
        }

        public ResponseEntity<Object> getDisplayListCart(Long userId) {
                CartMongo cartData = cartMongoRespository.findAllByUser_Id(userId)
                                .orElseThrow(() -> new DataNotFoundException("Data Cart tidak ditemukan"));

                int totalMenus = cartData.getMenus().size();
                int totalMakanan = cartData.getMenus().stream().mapToInt(data -> data.getQuantity()).sum();

                List<MenusCartInfoDTO> menuList = cartData.getMenus().stream().map(data -> new MenusCartInfoDTO(
                                data.getId(), data.getMenuName(), data.getLevel(), data.getHarga(),
                                data.getQuantity()))
                                .toList();

                CartListResponse dataResponse = CartListResponse.builder()
                                .cartId(cartData.getId())
                                .customer(new CustomerInfo(
                                                cartData.getUser().getId(), cartData.getUser().getFullname(),
                                                cartData.getUser().getAlamat()))
                                .resto(new RestoInfo(
                                                cartData.getResto().getId(), cartData.getResto().getName(),
                                                cartData.getResto().getAlamat(), cartData.getResto().getTimeOpen()))
                                .menus(menuList)
                                .totalHarga(cartData.getTotalHarga())
                                .totalMakanan(totalMakanan)
                                .build();

                ResponseBodyDTO response = ResponseBodyDTO.builder()
                                .total(totalMenus)
                                .data(dataResponse)
                                .message("Berhasil memuat data Cart")
                                .code(statusOk.value())
                                .status(statusOk.getReasonPhrase())
                                .build();

                return new ResponseEntity<>(response, statusOk);
        }
}
