package com.orderapp.foodorder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.orderapp.foodorder.dto.response.MenuListResponse;
import com.orderapp.foodorder.dto.response.ResponseBodyDTO;
import com.orderapp.foodorder.dto.response.MenuListResponse.Menus;
import com.orderapp.foodorder.dto.response.MenuListResponse.RestoInfo;
import com.orderapp.foodorder.exception.classes.DataNotFoundException;
import com.orderapp.foodorder.model.mongoDb.MenuMongo;
import com.orderapp.foodorder.repository.MenuMongoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {
    @Autowired
    MenuMongoRepository menuMongoRepository;

    private static final HttpStatus statusOk = HttpStatus.OK;
    private static final String messageSuccess = "Berhasil memuat data Menu";

    public ResponseEntity<Object> getMenuByRestoId(Long restoId) {
        List<MenuMongo> menus = menuMongoRepository.findAllByResto_Id(restoId);
        if (menus.isEmpty()) {
            throw new DataNotFoundException("Data Menu tidak ditemukan");
        }

        MenuMongo menu = menus.get(0);
        RestoInfo resto = new RestoInfo(menu.getResto().getId(),
                menu.getResto().getName(),
                menu.getResto().getAlamat(),
                menu.getResto().getTimeOpen());

        List<Menus> menuList = menus.stream().map(data -> new Menus(
                data.getId(), data.getName(), data.getRating(), data.getLevel(), data.getHarga())).toList();

        MenuListResponse datas = new MenuListResponse(resto, menuList);

        ResponseBodyDTO response = ResponseBodyDTO.builder()
                .total(menus.size())
                .data(datas)
                .message(messageSuccess)
                .code(statusOk.value())
                .status(statusOk.getReasonPhrase())
                .build();

        log.info(messageSuccess);

        return new ResponseEntity<>(response, statusOk);
    }
}
