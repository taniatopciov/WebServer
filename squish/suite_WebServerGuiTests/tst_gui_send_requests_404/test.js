import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    mouseClick(waitForObject(names.serverSettingsSTARTButton), 61, 19, 0, Button.Button1);
    // Send Request - Server Started - 404
    mouseClick(waitForObject(names.serverSettingsRequestTextFieldTextField), 156, 11, 0, Button.Button1);
    type(waitForObject(names.serverSettingsRequestTextFieldTextField), "http://localhost:90");
    mouseClick(waitForObject(names.serverSettingsSendButton), 33, 16, 0, Button.Button1);
    test.compare(waitForObjectExists(names.serverSettings404Label).text, "404");
}
