package com.yara.odx;

import com.yara.odx.collector.StatisticsCollector;
import com.yara.odx.domain.*;
import com.yara.odx.loader.PropertyGraphUploader;
import com.yara.odx.reader.ExcelWorkbookReader;
import com.yara.odx.reporter.StatisticsReporter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class UploadSeparatelyWithShaclMain {

    public static void main(String[] args) {
        Instant start = Instant.now();

//        Input your Uri, login and password as program arguments
        String uri = args[0];
        String login = args[1];
        String password = args[2];

        String shaclFileName = "loader/src/main/resources/yara_crop_shacl.ttl";

        String countryFileName = "loader/src/main/resources/Country.xlsx";
        String regionFileName = "loader/src/main/resources/Region.xlsx";
        String cropGroupFileName = "loader/src/main/resources/CropGroup.xlsx";
        String cropClassFileName = "loader/src/main/resources/CropClass.xlsx";
        String cropSubClassFileName = "loader/src/main/resources/CropSubClass.xlsx";
        String cropVarietyFileName = "loader/src/main/resources/CropVariety.xlsx";
        String cropDescriptionFileName = "loader/src/main/resources/CropDescription.xlsx";
        String cropDescriptionVarietyFileName = "loader/src/main/resources/CropDescriptionVariety.xlsx";
        String growthScaleFileName = "loader/src/main/resources/GrowthScale.xlsx";
        String growthScaleStageFileName = "loader/src/main/resources/GrowthScaleStages.xlsx";
        String cropRegionFileName = "loader/src/main/resources/CropRegion.xlsx";
        String nutrientFileName = "loader/src/main/resources/Nutrient.xlsx";
        String unitsFileName = "loader/src/main/resources/Units.xlsx";
        String unitConversionsFileName = "loader/src/main/resources/UnitConversion.xlsx";
        String fertilizersFileName = "loader/src/main/resources/Fertilizers.xlsx";
        String fertilizerRegionFileName = "loader/src/main/resources/Fertilizers_Reg.xlsx";

        ExcelWorkbookReader reader = new ExcelWorkbookReader();
        PropertyGraphUploader uploader = new PropertyGraphUploader(uri, login, password);
        StatisticsCollector collector = new StatisticsCollector();
        StatisticsReporter reporter = new StatisticsReporter();

        List<Country> countries = reader.readCountryFromExcel(countryFileName);
        List<Region> regions = reader.readRegionFromExcel(regionFileName);
        List<CropGroup> cropGroups = reader.readCropGroupFromExcel(cropGroupFileName);
        List<CropClass> cropClasses = reader.readCropClassFromExcel(cropClassFileName);
        List<CropSubClass> cropSubClasses = reader.readCropSubClassFromExcel(cropSubClassFileName);
        List<CropVariety> cropVarieties = reader.readCropVarietyFromExcel(cropVarietyFileName);
        List<CropDescription> cropDescriptions = reader.readCropDescriptionsFromExcel(cropDescriptionFileName);
        List<CropDescriptionVariety> cropDescVars = reader.readCropDescriptionVarietyFromExcel(cropDescriptionVarietyFileName);
        List<GrowthScale> growthScales = reader.readGrowthScaleFromExcel(growthScaleFileName);
        List<GrowthScaleStages> growthScaleStages = reader.readGrowthScaleStageFromExcel(growthScaleStageFileName);
        List<CropRegion> cropRegions = reader.readCropRegionsFromExcel(cropRegionFileName);
        List<Nutrient> nutrients = reader.readNutrientsFromExcel(nutrientFileName);
        List<Units> units = reader.readUnitsFromExcel(unitsFileName);
        List<UnitConversion> unitConversions = reader.readUnitConversionsFromExcel(unitConversionsFileName);
        List<Fertilizers> fertilizers = reader.readFertilizersFromExcel(fertilizersFileName);
        List<FertilizerRegion> fertilizerRegions = reader.readFertilizerRegionsFromExcel(fertilizerRegionFileName);

        collector.collectStatistics(countries);
        collector.collectStatistics(regions);
        collector.collectStatistics(cropGroups);
        collector.collectStatistics(cropClasses);
        collector.collectStatistics(cropSubClasses);
        collector.collectStatistics(cropVarieties);
        collector.collectStatistics(cropDescriptions);
        collector.collectStatistics(growthScales);
        collector.collectStatistics(growthScaleStages);
        collector.collectStatistics(nutrients);
        collector.collectStatistics(units);
        collector.collectStatistics(unitConversions);
        collector.collectStatistics(fertilizers);

        reporter.createStatisticsFile();
        reporter.writeStatisticsToFileAsJson(collector);

        uploader.uploadShaclInline(shaclFileName);
        uploader.activateShaclValidationOfTransactions();

//        Only for Neo4j Enterprise version 4.0
        uploader.dropConstraintsAndIndexes();
        uploader.createConstraintsAndIndexes();

        uploader.uploadCountriesAsBatch(countries);
        uploader.uploadRegionsAsBatch(regions, countries);
        uploader.uploadCropGroupsAsBatch(cropGroups);
        uploader.uploadCropClassAsBatch(cropClasses, cropGroups);
        uploader.uploadCropSubClassesAsBatch(cropSubClasses, cropClasses);
        uploader.uploadCropVarietiesAsBatch(cropVarieties, cropSubClasses);
        uploader.uploadCropDescriptionsAsBatch(cropDescriptions, cropSubClasses, cropRegions, growthScales);
        uploader.uploadGrowthScalesAsBatch(growthScales);
        uploader.uploadGrowthScaleStagesAsBatch(growthScaleStages, growthScales);
        uploader.uploadNutrientsAsBatch(nutrients);
        uploader.uploadUnitsAsBatch(units);
        uploader.uploadUnitConversionsAsBatch(unitConversions, units);
        uploader.uploadFertilizersAsBatch(fertilizers);

        uploader.createCountryToRegionRelations(countries, regions);
        uploader.createCropGroupToClassRelations(cropGroups, cropClasses);
        uploader.createCropClassToSubClassRelations(cropClasses, cropSubClasses);
        uploader.createCropSubClassToVarietyRelations(cropSubClasses, cropVarieties);
        uploader.createCropSubClassToDescriptionRelations(cropSubClasses, cropDescriptions);
        uploader.createCropVarietyToDescriptionRelations(cropVarieties, cropDescriptions, cropDescVars);
        uploader.createGrowthScaleToStagesRelations(growthScales, growthScaleStages);
        uploader.createCropDescriptionsToRegionsRelations(cropDescriptions, regions, cropRegions);
        uploader.createCropDescriptionsToGrowthScaleRelations(cropDescriptions, growthScales, cropRegions);
        uploader.createNutrientsToUnitsRelations(nutrients, units);
        uploader.createUnitsToConversionsRelations(units, unitConversions);
        uploader.createFertilizersToRegionsRelations(fertilizers, countries, regions, fertilizerRegions);
        uploader.createFertilizersToNutrientsRelations(fertilizers, nutrients, units);

        uploader.close();

        Instant finish = Instant.now();
        long elapsedTimeMillis = Duration.between(start, finish).toMillis();
        long elapsedTimeMinutes = Duration.between(start, finish).toMinutes();
        System.out.println("Total app runtime: " + elapsedTimeMillis + " milliseconds");
        System.out.println("Total app runtime: " + elapsedTimeMillis / 1000 + " seconds");
        System.out.println("Total app runtime: " + elapsedTimeMinutes + " minutes");
    }
}
