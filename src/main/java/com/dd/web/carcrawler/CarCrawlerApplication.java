package com.dd.web.carcrawler;

import com.dd.web.carcrawler.controllers.SaveAllCarsService;
import com.dd.web.carcrawler.controllers.SaveUsualCarsController;
import com.dd.web.carcrawler.entities.Manufacturer;
import com.dd.web.carcrawler.entities.usual.entities.UsualManufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class CarCrawlerApplication {
    private static SaveAllCarsService saveAllCarsService;
    private static SaveUsualCarsController saveUsualCarsController;

    @Autowired
    public CarCrawlerApplication(SaveAllCarsService saveAllCarsService, SaveUsualCarsController saveUsualCarsController) {
        this.saveAllCarsService = saveAllCarsService;
        this.saveUsualCarsController = saveUsualCarsController;
    }


    public static void main(String[] args) {
        SpringApplication.run(CarCrawlerApplication.class, args);

        boolean finished = false;

        while (!finished) {
            List<UsualManufacturer> usualUsualManufacturers = saveUsualCarsController.getAllManufacturers();
            try {
                saveUsualCarsController.prepare();
                UsualManufacturer lastUsualManufacturer = usualUsualManufacturers.stream().sorted(Comparator.comparing(UsualManufacturer::getName).reversed()).findFirst().orElse(null);
                finished = saveUsualCarsController.test(lastUsualManufacturer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                saveUsualCarsController.teardown();
            }
        }

        finished = false;

        while (!finished) {
            List<Manufacturer> manufacturers = saveAllCarsService.getAllManufacturers();

            try {
                saveAllCarsService.prepare();
                Manufacturer lastManufacturer = manufacturers.stream().sorted(Comparator.comparing(Manufacturer::getName).reversed()).findFirst().orElse(null);
                finished = saveAllCarsService.test(lastManufacturer);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                saveAllCarsService.teardown();
            }
        }

    }

}
