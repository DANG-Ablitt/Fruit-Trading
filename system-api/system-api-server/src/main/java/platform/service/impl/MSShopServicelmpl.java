package platform.service.impl;

import mybatis_plus.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import platform.dao.MSShopDao;
import platform.dto.ShopInfoDTO;
import platform.dto.ShopListDTO;
import platform.entity.MSShopEntity;
import platform.service.MSShopService;

import java.util.List;

@Service
public class MSShopServicelmpl extends BaseServiceImpl<MSShopDao, MSShopEntity> implements MSShopService {
    @Override
    public List<MSShopEntity> getByShop() {
        List<MSShopEntity> list = baseDao.getShopById();
        List<ShopListDTO> shop_list = null;
        // 对数据重新封装
        //list.forEach(e->{
            // 无关参数直接写入
            //ShopListDTO shopListDTO = new ShopListDTO();
            //shopListDTO.setId(e.getId());
            //shopListDTO.s
            //shop_list.add()

        //});
        return list;
    }

    @Override
    public ShopInfoDTO getShopByInfo(Long shop_id) {
        ShopInfoDTO shopInfoDTO = baseDao.getShopByInfo(shop_id);
        return shopInfoDTO;
    }
}
