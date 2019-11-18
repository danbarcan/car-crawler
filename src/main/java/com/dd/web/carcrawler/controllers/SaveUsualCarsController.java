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

    private PowerRepository powerRepository;
    private EngineSizeRepository engineSizeRepository;
    private ModelRepository modelRepository;
    private ManufacturerRepository manufacturerRepository;
    private FuelTypeRepository fuelTypeRepository;

    @Autowired
    public SaveUsualCarsController(PowerRepository powerRepository, EngineSizeRepository engineSizeRepository, ModelRepository modelRepository, ManufacturerRepository manufacturerRepository, FuelTypeRepository fuelTypeRepository) {
        this.powerRepository = powerRepository;
        this.engineSizeRepository = engineSizeRepository;
        this.modelRepository = modelRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.fuelTypeRepository = fuelTypeRepository;
    }

    public void prepare() {
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver.exe");

        testUrl = "https://www.epiesa.ro//";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get(testUrl);
    }

    public void test() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
        WebElement link = driver.findElement(By.linkText("find your car manually"));
        link.click();
        WebElement selectManufacturerElement = driver.findElement(By.name("select_marca"));
        Select selectManufacturer = new Select(selectManufacturerElement);
        manufacturerList = selectManufacturer.getOptions()
                .stream()
                .filter(manufacturer -> !manufacturer.getText().contains("MARCA"))
                .map(manufacturer -> new Manufacturer(manufacturer.getText()))
                .collect(Collectors.toList());

        manufacturerList.forEach(manufacturer -> {
            manufacturerRepository.save(manufacturer);
            selectManufacturer.selectByVisibleText(manufacturer.getName());
            WebElement selectModelElement = driver.findElement(By.name("select_model"));
            while (!selectModelElement.isEnabled()) ;
            Select selectModel = new Select(selectModelElement);
            manufacturer.setModels(selectModel.getOptions()
                    .stream()
                    .filter(model -> !model.getText().contains("MODEL"))
                    .map(model -> new Model(model.getText()))
                    .collect(Collectors.toList()));
            manufacturer.getModels().forEach(model -> {
                model.setManufacturer(manufacturer);
                modelRepository.save(model);
                selectModel.selectByVisibleText(model.getName());
                WebElement selectCarburantElement = driver.findElement(By.name("select_carburant"));
                while (!selectCarburantElement.isEnabled()) ;
                Select selectVariant = new Select(selectCarburantElement);
                model.setFuelTypes(selectVariant.getOptions()
                .stream()
                .filter(carburant -> !carburant.getText().contains("CARBURANT"))
                .map(carburant -> new FuelType(carburant.getText()))
                .collect(Collectors.toList()));

                model.getFuelTypes().forEach(fuelType -> {
                    fuelType.setModel(model);
                    fuelTypeRepository.save(fuelType);
                    selectVariant.selectByVisibleText(fuelType.getName());
                    WebElement selectEngineElement = driver.findElement(By.name("select_cilindree"));
                    while (!selectEngineElement.isEnabled()) ;
                    Select selectEngine = new Select(selectEngineElement);

                    fuelType.setEngineSizes(selectEngine.getOptions()
                            .stream()
                            .filter(engineSize -> !engineSize.getText().contains("CILINDREE"))
                            .map(engineSize -> new EngineSize(engineSize.getText()))
                            .collect(Collectors.toList()));

                    fuelType.getEngineSizes().forEach(engineSize -> {
                        engineSize.setFuelType(fuelType);
                        engineSizeRepository.save(engineSize);
                        selectEngine.selectByVisibleText(engineSize.getName());
                        WebElement selectYearElement;
                        selectYearElement = driver.findElement(By.name("select_motorizari"));
                        while (!selectYearElement.isEnabled()) ;

                        Select selectYear = new Select(selectYearElement);

                        engineSize.setPowers(selectYear.getOptions()
                                .stream()
                                .filter(year -> !year.getText().contains("PUTERE"))
                                .map(power -> {
                                    Power power1 = new Power(power.getText());
                                    power1.setEngineSize(engineSize);
                                    powerRepository.save(power1);
                                    return  power1;
                                })
                                .collect(Collectors.toList()));

                        //engineSizeRepository.save(engineSize);
                });



                });
                //modelRepository.save(model);
            });
            manufacturerRepository.save(manufacturer);
            System.out.println(manufacturer);
        });

        System.out.println("End");

    }

    public void teardown() {
        driver.quit();
    }

}
