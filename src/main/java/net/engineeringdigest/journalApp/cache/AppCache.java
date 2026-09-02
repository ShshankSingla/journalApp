package net.engineeringdigest.journalApp.cache;
import java.util.*;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

//    public  Map<String , String>appCache = new HashMap<>();
    public  Map<String , String>appCache;

    @PostConstruct  //as soon as Bean creted it will invoke
    public void init(){
        // this below line only when we are not initialziing above that 'new' line;
        appCache= new HashMap<>();

        //jitne bhi key value pairs exist karte hai respective repo mei sab aa jayenge
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();

        for(ConfigJournalAppEntity configJournalAppEntity: all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }

    }
}
