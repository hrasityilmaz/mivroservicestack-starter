package art.timestop.service.service;



import java.util.List;

import art.timestop.service.data.ServiceEntity;

public interface MainServiceService {
    List<ServiceEntity> getAlbums(String userId);
}
