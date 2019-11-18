package com.dd.web.carcrawler.controllers;

import com.dd.web.carcrawler.entities.EngineSize;
import com.dd.web.carcrawler.entities.Manufacturer;
import com.dd.web.carcrawler.entities.Model;
import com.dd.web.carcrawler.entities.Years;
import com.dd.web.carcrawler.repositories.EngineSizeRepository;
import com.dd.web.carcrawler.repositories.ManufacturerRepository;
import com.dd.web.carcrawler.repositories.ModelRepository;
import com.dd.web.carcrawler.repositories.YearsRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SaveAllCarsService {

    private String testUrl;
    private WebDriver driver;

    private YearsRepository yearsRepository;
    private EngineSizeRepository engineSizeRepository;
    private ModelRepository modelRepository;
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public SaveAllCarsService(YearsRepository yearsRepository, EngineSizeRepository engineSizeRepository, ModelRepository modelRepository, ManufacturerRepository manufacturerRepository) {
        this.yearsRepository = yearsRepository;
        this.engineSizeRepository = engineSizeRepository;
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    public void prepare() {
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver.exe");

        testUrl = "https://www.autopartsuk.com/";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get(testUrl);
    }

    public boolean test(Manufacturer lastManufacturer) {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        WebElement link = driver.findElement(By.linkText("find your car manually"));
        link.click();
        WebElement selectManufacturerElement = driver.findElement(By.id("car-manufacturer"));
        Select selectManufacturer = new Select(selectManufacturerElement);
        manufacturerList = selectManufacturer.getOptions()
                .stream()
                .filter(manufacturer -> !manufacturer.getText().equalsIgnoreCase("Make") && (lastManufacturer == null || lastManufacturer.getName().compareTo(manufacturer.getText()) < 0))
                .map(manufacturer -> new Manufacturer(manufacturer.getText()))
                .collect(Collectors.toList());

        manufacturerList.forEach(manufacturer -> {
            manufacturerRepository.save(manufacturer);
            selectManufacturer.selectByVisibleText(manufacturer.getName());
            WebElement selectModelElement = driver.findElement(By.id("car-model"));
            while (!selectModelElement.isEnabled()) ;
            Select selectModel = new Select(selectModelElement);
            List<Model> models = selectModel.getOptions()
                    .stream()
                    .filter(model -> !model.getText().equalsIgnoreCase("Model"))
                    .map(model -> new Model(model.getText()))
                    .collect(Collectors.toList());
            models.forEach(model -> {
                model.setManufacturer(manufacturer);
                modelRepository.save(model);
                selectModel.selectByVisibleText(model.getName());
                WebElement selectVariantElement = driver.findElement(By.id("car-variant"));
                while (!selectVariantElement.isEnabled()) ;
                Select selectVariant = new Select(selectVariantElement);
                selectVariant.selectByVisibleText("All");

                WebElement selectEngineElement = driver.findElement(By.id("car-engine-size"));
                while (!selectEngineElement.isEnabled()) ;
                Select selectEngine = new Select(selectEngineElement);

                List<EngineSize> engineSizes = selectEngine.getOptions()
                        .stream()
                        .filter(engineSize -> !engineSize.getText().equalsIgnoreCase("Engine Size"))
                        .map(engineSize -> new EngineSize(engineSize.getText()))
                        .collect(Collectors.toList());

                engineSizes.forEach(engineSize -> {
                    engineSize.setModel(model);
                    engineSizeRepository.save(engineSize);
                    selectEngine.selectByVisibleText(engineSize.getName());
                    WebElement selectYearElement;
                    try {
                        selectYearElement = driver.findElement(By.id("car-year-of-manufacture"));
                        while (!selectYearElement.isEnabled()) ;
                    } catch (WebDriverException e) {
                        selectYearElement = driver.findElement(By.id("car-year-of-manufacture"));
                        while (!selectYearElement.isEnabled()) ;
                    }
                    Select selectYear = new Select(selectYearElement);

                    List<Years> years = selectYear.getOptions()
                            .stream()
                            .filter(year -> !year.getText().equalsIgnoreCase("Year Of Manufacture"))
                            .map(year -> {
                                Years y = new Years(Integer.valueOf(year.getText()));
                                y.setEngineSize(engineSize);
                                yearsRepository.save(y);
                                return  y;
                            })
                            .collect(Collectors.toList());

//                    engineSize.setFuelTypes(years);
//                    engineSize.setFuelType(model);
//                    engineSizeRepository.save(engineSize);
                });
//                model.setFuelTypes(engineSizes);
//                model.setManufacturer(manufacturer);
//                modelRepository.save(model);
            });
//            manufacturer.setModels(models);
//            manufacturerRepository.save(manufacturer);
            System.out.println(manufacturer);
        });

        System.out.println("End");

        return true;
    }

    public void teardown() {
        driver.quit();
    }

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

}
