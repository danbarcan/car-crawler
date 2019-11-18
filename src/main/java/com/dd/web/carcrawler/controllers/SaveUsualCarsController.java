package com.dd.web.carcrawler.controllers;

import com.dd.web.carcrawler.entities.usual.entities.*;
import com.dd.web.carcrawler.repositories.usual.repositories.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SaveUsualCarsController {

    private String testUrl;
    private WebDriver driver;

    private UsualPowerRepository usualPowerRepository;
    private UsualEngineSizeRepository usualEngineSizeRepository;
    private UsualModelRepository usualModelRepository;
    private UsualManufacturerRepository usualManufacturerRepository;
    private UsualFuelTypeRepository usualFuelTypeRepository;

    @Autowired
    public SaveUsualCarsController(UsualPowerRepository usualPowerRepository, UsualEngineSizeRepository usualEngineSizeRepository, UsualModelRepository usualModelRepository, UsualManufacturerRepository usualManufacturerRepository, UsualFuelTypeRepository usualFuelTypeRepository) {
        this.usualPowerRepository = usualPowerRepository;
        this.usualEngineSizeRepository = usualEngineSizeRepository;
        this.usualModelRepository = usualModelRepository;
        this.usualManufacturerRepository = usualManufacturerRepository;
        this.usualFuelTypeRepository = usualFuelTypeRepository;
    }

    public void prepare() {
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver.exe");

        testUrl = "https://www.epiesa.ro/";

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get(testUrl);
    }

    public boolean test(UsualManufacturer lastUsualManufacturer) {
        List<UsualManufacturer> usualManufacturerList = new ArrayList<>();
        WebElement selectManufacturerElement = driver.findElement(By.name("select_marca"));
        Select selectManufacturer = new Select(selectManufacturerElement);
        usualManufacturerList = selectManufacturer.getOptions()
                .stream()
                .filter(manufacturer -> !manufacturer.getText().contains("MARCA")&& (lastUsualManufacturer == null || lastUsualManufacturer.getName().compareTo(manufacturer.getText()) < 0))
                .map(manufacturer -> new UsualManufacturer(manufacturer.getText()))
                .collect(Collectors.toList());

        usualManufacturerList.forEach(usualManufacturer -> {
            usualManufacturerRepository.save(usualManufacturer);
            selectManufacturer.selectByVisibleText(usualManufacturer.getName());
            WebElement selectModelElement = driver.findElement(By.name("select_model"));
            while (!selectModelElement.isEnabled()) ;
            Select selectModel = new Select(selectModelElement);
            usualManufacturer.setUsualModels(selectModel.getOptions()
                    .stream()
                    .filter(model -> !model.getText().contains("MODEL"))
                    .map(model -> new UsualModel(model.getText()))
                    .collect(Collectors.toList()));
            usualManufacturer.getUsualModels().forEach(usualModel -> {
                usualModel.setUsualManufacturer(usualManufacturer);
                usualModelRepository.save(usualModel);
                selectModel.selectByVisibleText(usualModel.getName());
                WebElement selectCarburantElement = driver.findElement(By.name("select_carburant"));
                while (!selectCarburantElement.isEnabled()) ;
                Select selectVariant = new Select(selectCarburantElement);
                usualModel.setUsualFuelTypes(selectVariant.getOptions()
                .stream()
                .filter(carburant -> !carburant.getText().contains("CARBURANT"))
                .map(carburant -> new UsualFuelType(carburant.getText()))
                .collect(Collectors.toList()));

                usualModel.getUsualFuelTypes().forEach(usualFuelType -> {
                    usualFuelType.setUsualModel(usualModel);
                    usualFuelTypeRepository.save(usualFuelType);
                    selectVariant.selectByVisibleText(usualFuelType.getName());
                    WebElement selectEngineElement = driver.findElement(By.name("select_cilindree"));
                    while (!selectEngineElement.isEnabled()) ;
                    Select selectEngine = new Select(selectEngineElement);

                    usualFuelType.setUsualEngineSizes(selectEngine.getOptions()
                            .stream()
                            .filter(engineSize -> !engineSize.getText().contains("CILINDREE"))
                            .map(engineSize -> new UsualEngineSize(engineSize.getText()))
                            .collect(Collectors.toList()));

                    usualFuelType.getUsualEngineSizes().forEach(usualEngineSize -> {
                        usualEngineSize.setUsualFuelType(usualFuelType);
                        usualEngineSizeRepository.save(usualEngineSize);
                        selectEngine.selectByVisibleText(usualEngineSize.getName());
                        WebElement selectYearElement;
                        selectYearElement = driver.findElement(By.name("select_motorizari"));
                        while (!selectYearElement.isEnabled()) ;

                        Select selectYear = new Select(selectYearElement);

                        usualEngineSize.setUsualPowers(selectYear.getOptions()
                                .stream()
                                .filter(year -> !year.getText().contains("PUTERE"))
                                .map(power -> {
                                    UsualPower usualPower1 = new UsualPower(power.getText());
                                    usualPower1.setUsualEngineSize(usualEngineSize);
                                    usualPowerRepository.save(usualPower1);
                                    return usualPower1;
                                })
                                .collect(Collectors.toList()));

                        //engineSizeRepository.save(usualEngineSize);
                });



                });
                //modelRepository.save(usualModel);
            });
            usualManufacturerRepository.save(usualManufacturer);
            System.out.println(usualManufacturer);
        });

        System.out.println("End");

        return true;
    }

    public void teardown() {
        driver.quit();
    }

    public List<UsualManufacturer> getAllManufacturers() {
        return usualManufacturerRepository.findAll();
    }
}
