import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    // Send Request - Stopped Server - Failure
    mouseClick(waitForObject(names.serverSettingsRequestTextFieldTextField), 126, 13, 0, Button.Button1);
    type(waitForObject(names.serverSettingsRequestTextFieldTextField), "http://localhost:90");
    mouseClick(waitForObject(names.serverSettingsSendButton), 16, 17, 0, Button.Button1);
    test.compare(waitForObjectExists(names.serverSettingsFAILEDLabel).text, "FAILED");
}
