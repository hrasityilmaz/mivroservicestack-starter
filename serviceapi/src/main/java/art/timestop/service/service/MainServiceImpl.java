package art.timestop.service.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import art.timestop.service.data.ServiceEntity;

@Service
public class MainServiceImpl implements MainServiceService {

    @Override
    public List<ServiceEntity> getAlbums(String userId) {
        List<ServiceEntity> returnValue = new ArrayList<>();
        
        ServiceEntity albumEntity = new ServiceEntity();
        albumEntity.setUserId(userId);
        albumEntity.setAlbumId("album1Id");
        albumEntity.setDescription("album 1 description");
        albumEntity.setId(1L);
        albumEntity.setName("album 1 name");
        
        ServiceEntity albumEntity2 = new ServiceEntity();
        albumEntity2.setUserId(userId);
        albumEntity2.setAlbumId("album2Id");
        albumEntity2.setDescription("album 2 description");
        albumEntity2.setId(2L);
        albumEntity2.setName("album 2 name");
        
        returnValue.add(albumEntity);
        returnValue.add(albumEntity2);
        
        return returnValue;
    }
    
}
