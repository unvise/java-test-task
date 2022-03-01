package com.unvise.transportbook.config;

import com.unvise.transportbook.entity.Transport;
import com.unvise.transportbook.repository.TransportRepository;
import com.unvise.transportbook.utils.Constants;
import com.unvise.transportbook.utils.ReadFromFile;
import com.unvise.transportbook.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class LoadDatabase {

    @Value(Constants.DB_DATA_FILE_PATH)
    private Resource resource;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDB(TransportRepository transportRepository) {
        return (args -> {
            List<List<String>> data = ReadFromFile
                    .readByLineFromFile(resource.getURL().getPath(), Constants.DB_DATA_FILE_SEPARATOR);

            for (List<String> list : data) {
                if (list.size() != 7) {
                    LOGGER.warn("Too few or too many params in list for creating a transport entity: " + list,
                            new IllegalStateException());
                    continue;
                }

                if (Validator.validateNoEmpty(list.get(0))
                        && Validator.validateNoEmpty(list.get(1))
                        && Validator.validateNoEmpty(list.get(2))
                        && Validator.validateLicensePlate(list.get(3), transportRepository)
                        && Validator.validateType(list.get(4))
                        && Validator.validateDate(list.get(5))
                        && Validator.validateTrailer(list.get(6))
                ) {
                    Transport t = new Transport(
                            list.get(0).trim().toUpperCase(),
                            list.get(1).trim().toUpperCase(),
                            list.get(2).trim().toUpperCase(),
                            list.get(3).trim().toUpperCase(),
                            list.get(4).trim().toUpperCase(),
                            list.get(5).trim().toUpperCase(),
                            list.get(6).trim().toUpperCase()
                    );
                    LOGGER.info("Preloading " + transportRepository.save(t));
                } else {
                    LOGGER.warn("Incorrect values for list: " + list, new IllegalStateException());
                }
            }
        });
    }

}
