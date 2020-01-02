//package network;
//
//import com.google.api.services.sheets.v4.Sheets;
//import com.google.api.services.sheets.v4.SheetsScopes;
//import com.google.api.services.sheets.v4.model.ValueRange;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//// All made using the googleSheets Quickstart thing
//public class SheetsAccess {
//
//    private static final JsonFactory jsonWizard = JacksonFactory.getDefaultInstance();
//    private static final List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);
//    private static final String credentialsPath = "/credentials.json";
//
//    // EFFECTS: creates and returns an authorized credentials object to access google sheets
//    private static Credential createAuthorization(final NetHttpTransport httpTransport) throws IOException {
//
//        // Grab the credentials for googlesheets from the previously downloaded JSON
//        InputStream input = SheetsAccess.class.getResourceAsStream(credentialsPath);
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonWizard, new InputStreamReader(input));
//
//        // Build authorization flow from google which will end up prompting a request for user credentials
//        GoogleAuthorizationCodeFlow.Builder flowBuilder = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, jsonWizard, clientSecrets, scopes);
//        flowBuilder.setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")));
//        flowBuilder.setAccessType("offline");
//        GoogleAuthorizationCodeFlow flow = flowBuilder.build();
//
//        // Make a receiver for the flow
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    // Should run through create the sheetService load my google sheet print out the stuff then change it
//    public static void main(String[] args) throws IOException, GeneralSecurityException {
//
//        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        final String googleSheetID = "1qtWHo2VBZlZDEjqQX_7Bg3y5WqzxQ57eSk2LhM1YXNc";
//
//        // Create a Sheets instance using the createAuthorization method to access testSheet
//        Sheets.Builder tempSheetBuilder = new Sheets.Builder(
//                httpTransport, jsonWizard, createAuthorization(httpTransport));
//        Sheets googleSheets = tempSheetBuilder.build();
//
//        // Use googleSheets to load some values then print them
//        Sheets.Spreadsheets.Values ssVals = googleSheets.spreadsheets().values();
//        ValueRange grabbedValues = ssVals.get(googleSheetID, "Sheet1!A1:B7").execute();
//        printSpreadSheetValues(grabbedValues);
//        addRowToSheet(googleSheetID, ssVals);
//
//    }
//
//    private static void addRowToSheet(String googleSheetID, Sheets.Spreadsheets.Values ssVals) throws IOException {
//        // Make a new row to show that we can change stuff in the sheet
//        List<String> innerlist = Arrays.asList("duck", "duck", "duck", "goose");
//        List<Object> cantthinkofabetterway = new ArrayList<Object>();
//        Object tempobject = null;
//        for (String s : innerlist) {
//            tempobject = s;
//            cantthinkofabetterway.add(tempobject);
//        }
//        List<List<Object>> convertedList = Arrays.asList(cantthinkofabetterway);
//        ValueRange newValues = new ValueRange().setValues(convertedList);
//
//        ssVals.append(googleSheetID, "Sheet1", newValues)
//                .setValueInputOption("USER_ENTERED")
//                .setInsertDataOption("INSERT_ROWS")
//                .execute();
//    }
//
//    private static void printSpreadSheetValues(ValueRange grabbedValues) {
//        List<List<Object>> values = grabbedValues.getValues();
//        int countRow = 1;
//        for (List list : values) {
//            System.out.println("Row: " + countRow + " FirstVal: " + list.get(0) + " SecondVal: " + list.get(1));
//            countRow++;
//        }
//    }
//}
