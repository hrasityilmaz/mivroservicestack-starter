package art.timestop.service.io.controllers;


import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.timestop.service.data.ServiceEntity;
import art.timestop.service.service.MainServiceService;
import art.timestop.service.ui.model.ServiceResponseModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/users/{id}/albums")
public class ServiceController {
    
    @Autowired
    MainServiceService albumsService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping( 
            produces = { 
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
            })
    public List<ServiceResponseModel> userAlbums(@PathVariable String id) {

        List<ServiceResponseModel> returnValue = new ArrayList<>();
        
        List<ServiceEntity> albumsEntities = albumsService.getAlbums(id);
        
        if(albumsEntities == null || albumsEntities.isEmpty())
        {
            return returnValue;
        }
        
        Type listType = new TypeToken<List<ServiceResponseModel>>(){}.getType();
 
        returnValue = new ModelMapper().map(albumsEntities, listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return returnValue;
    }
}
