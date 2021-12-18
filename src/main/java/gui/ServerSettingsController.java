package gui;

import config.ConfigFile;
import config.ConfigFileReader;
import config.ConfigFileWriter;
import config.ServerState;
import io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Server;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerSettingsController implements Initializable {

    @FXML
    private Label server_status;

    @FXML
    private Label server_address;

    @FXML
    private Label server_port;

    @FXML
    private Label request_label;

    @FXML
    private TextField port_text_field;

    @FXML
    private TextField web_root_directory_text_field;

    @FXML
    private TextField maintenance_directory_text_field;

    @FXML
    private TextField request_text_field;

    @FXML
    private Button start_server_button;

    @FXML
    private Button stop_server_button;

    @FXML
    private Button select_web_root_directory_button;

    @FXML
    private Button select_maintenance_directory_button;

    @FXML
    private Button send_request_button;

    @FXML
    private Button apply_changes_button;

    @FXML
    private CheckBox switch_to_maintenance_checkbox;

    @FXML
    private AnchorPane anchor_pane;

    private DirectoryChooser directoryChooser;
    private FileChooser fileChooser;

    private ConfigFileReader configFileReader;
    private ConfigFileWriter configFileWriter;
    private FileContentReader fileContentReader;
    private FileContentWriter fileContentWriter;
    private JsonParser jsonParser;
    private Thread serverThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        directoryChooser = new DirectoryChooser();
        fileChooser = new FileChooser();

        fileContentReader = new FileContentReaderImpl();
        fileContentWriter = new FileContentWriterImpl();
        jsonParser = new JsonParserImpl(fileContentReader);

        configFileReader = new ConfigFileReader(jsonParser);

        configFileWriter = new ConfigFileWriter(fileContentWriter);
        ConfigFile configFile = configFileReader.readConfigFile();

        stop_server_button.setDisable(true);
        start_server_button.setDisable(false);
        switch_to_maintenance_checkbox.setDisable(true);

        server_status.setText(ServerState.STOPPED.toString());
        server_port.setText(configFile.getPortNumber());

        port_text_field.setText(configFile.getPortNumber());
        web_root_directory_text_field.setText(configFile.getRootFolder());
        maintenance_directory_text_field.setText(configFile.getMaintenanceFilePath());

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            start_server_button.requestFocus();
                            cancel();
                        });
                    }
                },
                100
        );
    }

    @FXML
    void startButtonOnClick(ActionEvent event) {
        stop_server_button.setDisable(false);
        stop_server_button.requestFocus();
        start_server_button.setDisable(true);
        switch_to_maintenance_checkbox.setDisable(false);
        switch_to_maintenance_checkbox.setSelected(false);

        ConfigFile configFile = new ConfigFile(port_text_field.getText(), web_root_directory_text_field.getText(), maintenance_directory_text_field.getText(), ServerState.valueOf(server_status.getText()));

        serverThread = new Thread(() -> new Server(configFile).startServer());
        serverThread.start();

        server_status.setText(ServerState.RUNNING.toString());
    }

    @FXML
    void stopButtonOnClick(ActionEvent event) {
        stop_server_button.setDisable(true);
        start_server_button.requestFocus();
        start_server_button.setDisable(false);
        switch_to_maintenance_checkbox.setDisable(true);
        switch_to_maintenance_checkbox.setSelected(false);

        server_status.setText(ServerState.STOPPED.toString());

        sendServerState(ServerState.STOPPED);
    }

    @FXML
    void selectRootDirectoryButtonOnClick(ActionEvent event) {
        Stage stage = (Stage) anchor_pane.getScene().getWindow();

        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            web_root_directory_text_field.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void selectMaintenanceDirectoryButtonOnClick(ActionEvent event) {
        Stage stage = (Stage) anchor_pane.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            maintenance_directory_text_field.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void sendRequestButtonOnClick(ActionEvent event) {
        try {
            String urlString = request_text_field.getText();
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                request_label.setText("OK");
            } else {
                request_label.setText(responseCode + "");
            }
        } catch (IOException e) {
            request_label.setText("FAILED");
            e.printStackTrace();
        }
    }

    @FXML
    void switchToMaintenanceCheckboxOnClick(ActionEvent event) {
        if (switch_to_maintenance_checkbox.isSelected()) {
            server_status.setText(ServerState.MAINTENANCE.toString());
            sendServerState(ServerState.MAINTENANCE);
        } else {
            server_status.setText(ServerState.RUNNING.toString());
            sendServerState(ServerState.RUNNING);
        }
    }

    @FXML
    void applyChangesButtonOnClick(ActionEvent event) {
        server_port.setText(port_text_field.getText());

        ServerState state = ServerState.valueOf(server_status.getText());

        ConfigFile configFile = new ConfigFile(port_text_field.getText(), web_root_directory_text_field.getText(), maintenance_directory_text_field.getText(), state);

        try {
            configFileWriter.writeConfigFile(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (state != ServerState.STOPPED) {
            try {
                String urlString = "http://localhost:" + server_port.getText() + "/configserver";
                URL url = new URL(urlString);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("ServerState", state.toString());
                con.setRequestProperty("MaintenanceFilePath", maintenance_directory_text_field.getText());
                con.setRequestProperty("RootFolder", web_root_directory_text_field.getText());
                con.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendServerState(ServerState state) {
        try {
            String urlString = "http://localhost:" + server_port.getText() + "/configserver";
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("ServerState", state.toString());
            con.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
