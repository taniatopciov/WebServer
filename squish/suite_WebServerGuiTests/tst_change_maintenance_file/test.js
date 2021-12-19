import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    test.compare(waitForObjectExists(names.maintenanceDirectoryTextFieldCUsersTaniaDesktopTestSiteVVSMaintenanceHtmlText).text, "C:\\Users\\tania\\Desktop\\TestSiteVVS\\maintenance.html");
    // Open Choose File Dialog
    mouseClick(waitForObject(names.serverSettingsButton_2), 14, 16, 0, Button.Button1);
    chooseFile(waitForObject(names.squishJavaFX), "C:\\Users\\tania\\Documents\\score.txt");
    // Click Apply Changes
    mouseClick(waitForObject(names.serverSettingsApplyChangesButton), 28, 15, 0, Button.Button1);
    test.compare(waitForObjectExists(names.maintenanceDirectoryTextFieldCUsersTaniaDocumentsScoreTxtText).text, "C:\\Users\\tania\\Documents\\score.txt");
}
