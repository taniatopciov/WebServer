import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    test.compare(waitForObjectExists(names.webRootDirectoryTextFieldCUsersTaniaDesktopSO2021CorinaDirText).text, "C:\\Users\\tania\\Desktop\\SO2021\\Corina\\dir");
    // Open Choose Directory Dialog
    mouseClick(waitForObject(names.serverSettingsButton), 13, 20, 0, Button.Button1);
    chooseDirectory(waitForObject(names.squishJavaFX), "C:\\Users\\tania\\Desktop\\TestSiteVVS\\TestSite");
    // Click Apply Changes
    mouseClick(waitForObject(names.serverSettingsApplyChangesButton), 28, 15, 0, Button.Button1);
    test.compare(waitForObjectExists(names.webRootDirectoryTextFieldCUsersTaniaDesktopTestSiteVVSTestSiteText).text, "C:\\Users\\tania\\Desktop\\TestSiteVVS\\TestSite");
}
