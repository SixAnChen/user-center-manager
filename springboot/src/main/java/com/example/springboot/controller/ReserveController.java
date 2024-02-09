package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Hotel;
import com.example.springboot.entity.Params;
import com.example.springboot.entity.Reserve;
import com.example.springboot.mapper.HotelMapper;
import com.example.springboot.service.ReserveService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Resource
    private ReserveService reserveService;
    @Resource
    private HotelMapper hotelDao;

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Reserve> info = reserveService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping
    public Result save(@RequestBody Reserve reserve) {
        // 1. 酒店剩余房间是否为0，大于0的时候，才可以被预定
        Hotel hotel = hotelDao.selectByPrimaryKey(reserve.getHotelId());
        if (hotel.getNum() == 0) {
            return Result.error("酒店该房间以预定完，请预定其他房间");
        }

        // 2. 往预定表里插入一条预定记录
        reserve.setTime(DateUtil.now());
        reserveService.add(reserve);

        // 3. 对应的酒店房间剩余数量减一
        hotel.setNum(hotel.getNum() - 1);
        hotelDao.updateByPrimaryKeySelective(hotel);

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        reserveService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Reserve> list) {
        for (Reserve reserve : list) {
            reserveService.delete(reserve.getId());
        }
        return Result.success();
    }
}
