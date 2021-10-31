package main;

import config.ConfigFile;
import config.ConfigFileWriter;
import config.ServerState;
import io.FileContentReaderImpl;
import org.apache.commons.httpclient.HttpStatus;
import request.HeaderParser;
import request.HeaderParserImpl;
import request.RequestStartLine;
import request.RequestStartLineParser;
import resource_manager.*;
import response.HeaderBuilder;
import response.HeaderBuilderImpl;
import response.ResponseStatusLine;
import response.ResponseStatusLineBuilder;

import java.io.*;
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

                Thread thread = new Thread(() -> handleConnection(clientSocket));
                thread.start();
            }

        } catch (IOException e) {

        }
    }

    private void handleConnection(Socket socket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(socket.getOutputStream());

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

                if (requestStartLine.method.equals("POST") && requestStartLine.target.equals("/configserver")) {

                    HashMap<String, String> requestHeaders = headerParser.parseHeaders(requestHeadersList);
                    boolean modified = false;

                    if (requestHeaders.containsKey("ServerState")) {
                        ServerState serverState = ServerState.valueOf(requestHeaders.get("ServerState"));
                        configFile.setState(serverState);
                        modified = true;
                    }
                    if (requestHeaders.containsKey("MaintenanceFilePath")) {
                        String maintenanceFilePath = requestHeaders.get("MaintenanceFilePath");
                        configFile.setMaintenanceFilePath(maintenanceFilePath);
                        modified = true;
                    }
                    if (requestHeaders.containsKey("RootFolder")) {
                        String rootFolder = requestHeaders.get("RootFolder");
                        configFile.setRootFolder(rootFolder);
                        modified = true;
                    }

                    if (modified) {
                        configFileWriter.writeConfigFile(configFile);
                    }

                    responseHeaders.put("Content-Type", "text/html; charset=UTF-8");

                    writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_OK), responseHeaders, resourceAbsolutePathProvider.getResourceAbsolutePath("DefaultResponses/200.html"));

                    closeConnection(dataOut, socket);

                    if (configFile.getState() == ServerState.Stopped) {
                        System.exit(0);
                    }

                    return;

                } else {
                    responseHeaders.put("Content-Type", "text/html; charset=UTF-8");

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
        ResponseStatusLineBuilder responseStatusLineBuilder = new ResponseStatusLineBuilder();
        HeaderBuilder headerBuilder = new HeaderBuilderImpl();

        String responseLine = responseStatusLineBuilder.buildResponseStatusLine(responseStatusLine);
        String responseHeaders = headerBuilder.buildHeaders(headers);

        try {
            outStream.write(responseLine.getBytes(), 0, responseLine.getBytes().length);
            outStream.write(responseHeaders.getBytes(), 0, responseHeaders.getBytes().length);

            byte[] bodyContentBytes = new FileContentReaderImpl().readFileContent(pathToBody);

            outStream.write(bodyContentBytes, 0, bodyContentBytes.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(BufferedOutputStream outStream, Socket socket) throws IOException {
        outStream.flush();
        socket.close();
    }
}