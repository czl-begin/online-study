package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * banner后台管理接口
 * </p>
 *
 * @author testjava
 * @since 2021-07-27
 */
@RestController
@RequestMapping("/educms/crm-banneradmin")
@CrossOrigin
public class BannerAdminController {
   @Autowired
   private CrmBannerService crmBannerService;
    //1分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> pageBanner=new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);
        return R.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }
    //2添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }
    @ApiOperation(value="获取Banner")
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable String id){
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok();
    }
    //3修改banner
    @ApiOperation(value="修改Banner")
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.updateById(crmBanner);
        return R.ok();
    }
    //4删除banner
    @ApiOperation(value="删除Banner")
    @DeleteMapping("removeBanner/{id}")
    public R removeBanner(@PathVariable String id){
        crmBannerService.removeById(id);
        return R.ok();
    }
}

