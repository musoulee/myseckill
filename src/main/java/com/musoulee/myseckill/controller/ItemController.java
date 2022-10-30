package com.musoulee.myseckill.controller;

import com.musoulee.myseckill.common.CommonErrorEnum;
import com.musoulee.myseckill.common.ResponseModel;
import com.musoulee.myseckill.entity.Item;
import com.musoulee.myseckill.entity.Promotion;
import com.musoulee.myseckill.service.ItemService;
import com.musoulee.myseckill.util.ToolBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 商品Controller
 * @author: musou
 * @Date: 2022/10/24 08:59
 */
@Controller
@RequestMapping("/item")
@CrossOrigin(origins = "${myseckill.web.path}", allowedHeaders = "*", allowCredentials = "true")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 获取商品列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public ResponseModel getItemList() {
        List<Item> items = itemService.findItemsOnPromotion();
        return ResponseModel.createSuccess(items);
    }

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/detail/{id}", method = RequestMethod.GET)
    public ResponseModel getDetail(@PathVariable("id") String id) {
        Item item = itemService.getDetailInCache(id);
//        Item item = itemService.getDetailByID(id);
        if (item == null) {
            return ResponseModel.createFailure(CommonErrorEnum.STOCK_NOT_EXISTS);
        }
        return ResponseModel.createSuccess(item);
    }

    /**
     * 添加一个商品
     *
     * @param item
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseModel addItem(@RequestBody @Validated Item item) {
        int result = itemService.addItem(item);
        if (result < 1) {
            return ResponseModel.createFailure(CommonErrorEnum.ITEM_ADD_FAIL);
        }
        return ResponseModel.create();
    }

    @ResponseBody
    @RequestMapping(path = "/addpromotion", method = RequestMethod.POST)
    public ResponseModel addPromotion(@RequestBody @Validated Promotion promotion) {
        String itemId = promotion.getItemId();
        Item item = itemService.selectByPrimaryKey(itemId);
        if (item == null) {
            return ResponseModel.createFailure(CommonErrorEnum.PROMOTION_ADD_FAIL);
        }
        int result = itemService.insertPromotion(promotion);
        return ResponseModel.create();
    }
}
