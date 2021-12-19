import * as names from 'names.js';

function main() {
    startApplication("WebServer.jar");
    // Port 90 before changes
    test.compare(waitForObjectExists(names.serverSettings90Label).text, "90");
    mouseClick(waitForObject(names.serverSettingsPortTextFieldTextField), 56, 15, 0, Button.Button1);
    type(waitForObject(names.serverSettingsPortTextFieldTextField), "<Backspace>");
    type(waitForObject(names.serverSettingsPortTextFieldTextField), "<Backspace>");
    // Insert 8080 in port text field
    type(waitForObject(names.serverSettingsPortTextFieldTextField), "8080");
    // Click Apply Changes Button
    mouseClick(waitForObject(names.serverSettingsApplyChangesButton), 59, 16, 0, Button.Button1);
    test.compare(waitForObjectExists(names.serverSettings8080Label).text, "8080");
}
