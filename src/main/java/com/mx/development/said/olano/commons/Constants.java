package com.mx.development.said.olano.commons;

public class Constants {

    // ------ JOBS ------
    public static final String CLEAN_UP_WORKING_DIRECTORY_OS_JOB = "cleanUpWorkingDirectoryOSJob";
    public static final String RETRIEVE_NEWS_JOB = "retrieveNewsJob";
    public static final String NEWS_PROCESS_JOB = "newsProcessJob";
    // ------ JOBS ------
    // ------ STEPS ------
    public static final String DELETE_EXISTING_FILES_STEP = "deleteExistingFilesStep";
    public static final String RETRIEVE_NEWS_STEP = "retrieveNewsStep";
    public static final String CLEAN_UP_AND_FORMAT_NEWS_STEP = "cleanUpAndFormatNewsStep";
    public static final String CREATE_CSV_NEWS_STEP = "createCSVNewsStep";
    public static final String NEWS_PROCESS_STEP = "newsProcessStep";
    // ------ STEPS ------
    public static final String JOB_ID = "JobID";
    public static final String WORKING_DIRECTORY = "/Users/josesaidolanogarcia/temp/";

    public static final String CSV_FILE_01_PATH = WORKING_DIRECTORY + "cleanup_data_v1.csv";
    public static final String HEADERS_CSV_FILE_01 = "id,author,type,typeVersionNumber,feedTitle,category,entryTitle";

    public static final String COMMA_SEPARATOR = ",";
    public static final String WHITE_SPACE = " ";
}
