import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    mouseClick(waitForObject(names.serverSettingsSTARTButton), 31, 23, 0, Button.Button1);
    // Send Request - Stopped Server - OK
    mouseClick(waitForObject(names.serverSettingsRequestTextFieldTextField), 292, 15, 0, Button.Button1);
    type(waitForObject(names.serverSettingsRequestTextFieldTextField), "http://localhost:90/file.txt");
    mouseClick(waitForObject(names.serverSettingsSendButton), 16, 9, 0, Button.Button1);
    test.compare(waitForObjectExists(names.serverSettingsOKLabel).text, "OK");
}
