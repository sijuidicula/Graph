package com.yara.ss.tmp;

import com.yara.ss.domain.*;
import com.yara.ss.loader.PropertyGraphUploader;
import com.yara.ss.reader.ExcelWorkbookReader;

import java.util.List;

public class UploadSeparatelyMain {

    public static void main(String[] args) {
        String countryFileName = "loader/src/main/resources/Country.xlsx";
        String regionFileName = "loader/src/main/resources/Region.xlsx";
        String cropGroupFileName = "loader/src/main/resources/CropGroup.xlsx";
        String cropClassFileName = "loader/src/main/resources/CropClass.xlsx";
        String cropSubClassFileName = "loader/src/main/resources/CropSubClass.xlsx";
        String cropVarietyFileName = "loader/src/main/resources/CropVariety.xlsx";

        ExcelWorkbookReader reader = new ExcelWorkbookReader();
        PropertyGraphUploader uploader = new PropertyGraphUploader();

        List<Country> countries = reader.readCountryFromExcel(countryFileName);
        List<Region> regions = reader.readRegionFromExcel(regionFileName);
        List<CropGroup> cropGroups = reader.readCropGroupFromExcel(cropGroupFileName);
        List<CropClass> cropClasses = reader.readCropClassFromExcel(cropClassFileName);
        List<CropSubClass> cropSubClasses = reader.readCropSubClassFromExcel(cropSubClassFileName);
        List<CropVariety> cropVarieties = reader.readCropVarietyFromExcel(cropVarietyFileName);

//        uploader.uploadCountries(countries);
//        uploader.uploadRegions(regions);
//        uploader.createCountryToRegionRelations(countries, regions);
//        uploader.uploadCropGroups(cropGroups);
//        uploader.uploadCropClasses(cropClasses);
//        uploader.createCropGroupToClassRelations(cropGroups, cropClasses);
//        uploader.uploadCropSubClasses(cropSubClasses);
//        uploader.createCropClassToSubClassRelations(cropClasses, cropSubClasses);
//        uploader.uploadCropVarieties(cropVarieties, cropDescriptions, cropDescVars);
//        uploader.createCropSubClassToVarietyRelations(cropSubClasses, cropVarieties);

        uploader.close();
    }
}
