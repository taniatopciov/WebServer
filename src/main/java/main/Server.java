package main;

import config.ConfigFile;
import io.FileContentReaderImpl;
import org.apache.commons.httpclient.HttpStatus;
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
    private ServerSocket serverSocket;
    private RequestStartLineParser requestStartLineParser;
    private ResourcePathManager resourcePathManager;
    private ContentTypeProvider contentTypeProvider;

    public Server(ConfigFile configFile) {

        this.configFile = configFile;
        requestStartLineParser = new RequestStartLineParser();
        resourcePathManager = new ResourcePathManagerImpl(new ResourceAbsolutePathProviderImpl(), new ResourceFileManagerImpl());
        contentTypeProvider = new ContentTypeProviderImpl();
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

            if (requestStartLine == null) {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/html; charset=UTF-8");
                writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_BAD_REQUEST), headers, getClass().getResource("/DefaultResponses/400.html").getPath());
                dataOut.flush();
                socket.close();
                return;
            }

            if (!requestStartLine.method.equals("GET") && !requestStartLine.method.equals("HEAD")) {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "text/html; charset=UTF-8");
                writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_METHOD_NOT_ALLOWED), headers, getClass().getResource("/DefaultResponses/501.html").getPath());
                dataOut.flush();
                socket.close();
                return;
            }

            String line = in.readLine();

            ArrayList<String> requestHeadersList = new ArrayList<String>();

            while (!line.isEmpty()) {
                requestHeadersList.add(line);
                line = in.readLine();
            }

            HashMap<String, String> responseHeaders = new HashMap<>();

            String pathToResponseBody = resourcePathManager.getResourcePath(configFile.getRootFolder(), requestStartLine.target);
            String contentType = contentTypeProvider.getContentType(pathToResponseBody);

            responseHeaders.put("Content-Type", contentType);

            writeResponseToSocket(dataOut, new ResponseStatusLine("HTTP/1.1", HttpStatus.SC_OK), responseHeaders, pathToResponseBody);

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
}