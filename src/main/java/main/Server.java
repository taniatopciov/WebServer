package main;

import config.ConfigFile;
import config.ConfigFileWriter;
import config.ServerState;
import org.apache.commons.httpclient.HttpStatus;
import request.*;
import resource_manager.*;
import response.ResponseByteArrayProviderImpl;
import response.ResponseStatusLine;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private ConfigFile configFile;
    private ConfigFileWriter configFileWriter;
    private ServerSocket serverSocket;
    private RequestStartLineParser requestStartLineParser;
    private ResourcePathManager resourcePathManager;
    private ContentTypeProvider contentTypeProvider;
    private HeaderParser headerParser;
    private ResourceAbsolutePathProvider resourceAbsolutePathProvider;

    public Server(ConfigFile configFile) {
        this.configFile = configFile;
        requestStartLineParser = new RequestStartLineParser();
        resourcePathManager = new ResourcePathManagerImpl(new ResourceAbsolutePathProviderImpl(), new ResourceFileManagerImpl());
        contentTypeProvider = new ContentTypeProviderImpl();
        headerParser = new HeaderParserImpl();
        configFileWriter = new ConfigFileWriter();
        resourceAbsolutePathProvider = new ResourceAbsolutePathProviderImpl();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(configFile.getPortNumber()));

            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> handleConnection(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleConnection(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream())
        ) {
            RequestStartLine requestStartLine = requestStartLineParser.parseRequestStartLine(in.readLine());

            HashMap<String, String> responseHeaders = new HashMap<>();

            if (requestStartLine == null) {
                responseHeaders.put("Content-Type", "text/html; charset=UTF-8");

                writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_BAD_REQUEST), responseHeaders, resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/404.html"));

                closeConnection(dataOut, socket);
                return;
            }

            String line = in.readLine();

            ArrayList<String> requestHeadersList = new ArrayList<String>();

            while (!line.isEmpty()) {
                requestHeadersList.add(line);
                line = in.readLine();
            }

            if (!requestStartLine.method.equals("GET") && !requestStartLine.method.equals("HEAD")) {

                responseHeaders.put("Content-Type", "text/html; charset=UTF-8");

                if (requestStartLine.method.equals("POST") && requestStartLine.target.equals("/configserver")) {

                    ConfigFile newConfigFile = new PostRequestHandlerImpl().getNewConfigFile(configFile, headerParser.parseHeaders(requestHeadersList));

                    if (!configFile.equals(newConfigFile)) {
                        configFileWriter.writeConfigFile(newConfigFile);
                        configFile = newConfigFile;
                    }

                    writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_OK), responseHeaders, resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/200.html"));

                    closeConnection(dataOut, socket);

                    if (configFile.getState() == ServerState.Stopped) {
                        System.exit(0);
                    }

                    return;

                } else {

                    writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_METHOD_NOT_ALLOWED), responseHeaders, resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/405.html"));

                    closeConnection(dataOut, socket);
                    return;
                }
            }

            String pathToResponseBody;

            if (configFile.getState() == ServerState.Maintenance) {
                pathToResponseBody = configFile.getMaintenanceFilePath();
            } else {
                pathToResponseBody = resourcePathManager.getResourcePath(configFile.getRootFolder(), requestStartLine.target);
            }

            String contentType = contentTypeProvider.getContentType(pathToResponseBody);

            responseHeaders.put("Content-Type", contentType);

            writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_OK), responseHeaders, pathToResponseBody);

            closeConnection(dataOut, socket);
            return;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResponseToSocket(BufferedOutputStream outStream, ResponseStatusLine responseStatusLine, HashMap<String, String> headers, String pathToBody) {
        try {
            byte[] responseBytes = new ResponseByteArrayProviderImpl().getResponseBytes(responseStatusLine, headers, pathToBody);

            outStream.write(responseBytes, 0, responseBytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(BufferedOutputStream outStream, Socket socket) throws IOException {
        outStream.flush();
        socket.close();
    }
}