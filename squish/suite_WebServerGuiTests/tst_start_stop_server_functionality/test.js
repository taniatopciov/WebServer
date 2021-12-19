import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    // Currently Stopped Server
    test.compare(waitForObjectExists(names.sTARTSTARTText).disabled, false);
    test.compare(waitForObjectExists(names.serverSettingsSTOPButton).disabled, true);
    test.compare(waitForObjectExists(names.serverSettingsSwitchToMaintenanceModeCheckBox).disabled, true);
    mouseClick(waitForObject(names.serverSettingsSTARTButton), 50, 18, 0, Button.Button1);
    // Currently Server Running
    test.compare(waitForObjectExists(names.serverSettingsSTARTButton).disabled, true);
    test.compare(waitForObjectExists(names.serverSettingsSTOPButton).disabled, false);
    test.compare(waitForObjectExists(names.serverSettingsSwitchToMaintenanceModeCheckBox).disabled, false);
}
